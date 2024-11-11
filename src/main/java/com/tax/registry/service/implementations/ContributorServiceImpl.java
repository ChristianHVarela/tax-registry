package com.tax.registry.service.implementations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tax.registry.dto.ContributorDTO;
import com.tax.registry.exceptions.TaxRegistryException;
import com.tax.registry.model.Address;
import com.tax.registry.model.Contributor;
import com.tax.registry.model.embeddables.CompanyData;
import com.tax.registry.model.embeddables.PersonalData;
import com.tax.registry.modelEnum.PersonType;
import com.tax.registry.repositories.ContributorRepository;
import com.tax.registry.service.ContributorService;
import com.tax.registry.utils.ValidationUtils;
import com.tax.registry.validator.ContributorValidator;

@Service
public class ContributorServiceImpl implements ContributorService {
	
	private static final String DEFAULT_SORT_COLUMN = "identification";
	private final ContributorRepository repository;
	private final ContributorValidator validator;
	
	public ContributorServiceImpl(ContributorRepository contributorRepository, ContributorValidator contributorValidator) {
		this.repository = contributorRepository;
		this.validator = contributorValidator;
	}

	@Override
	public List<ContributorDTO> findContributors(Integer page, Integer size, String sortBy, String sortOrder) {
		if (page < 0) {
			throw new TaxRegistryException(HttpStatus.BAD_REQUEST, "Page number must be non-negative");
	    }
	    if (size <= 0) {
	        throw new TaxRegistryException(HttpStatus.BAD_REQUEST, "Size must be greater than 0");
	    }
		
		Sort.Direction direction = getSortDirection(sortOrder);
	    String sortColumn = StringUtils.trimToNull(sortBy) != null ? sortBy : DEFAULT_SORT_COLUMN;
	    
	    Sort sort = Sort.by(direction, sortColumn);
	    PageRequest pageRequest = PageRequest.of(page, size, sort);
	    
	    Page<Contributor> contributors = repository.findAllEnable(pageRequest);
	    
	    return contributors.getContent().stream().map(this::convertToDTO).toList();
	}
	
	private Sort.Direction getSortDirection(String sortOrder) {
	    return StringUtils.trimToNull(sortOrder) != null && sortOrder.equalsIgnoreCase("desc") ? 
	            Sort.Direction.DESC : Sort.Direction.ASC;
	}

	private ContributorDTO convertToDTO(Contributor contributor) {
	    return ContributorDTO.fromEntity(contributor);
	}

	@Override
	public ContributorDTO createContributor(ContributorDTO contributorDTO) {
		validateContributorDTO(contributorDTO, null);
		
		Contributor contributor = new Contributor(contributorDTO);
		contributor = repository.save(contributor);
		
		return ContributorDTO.fromEntity(contributor);
	}

	private void validateContributorDTO(ContributorDTO contributorDTO, Contributor contributor) {
	    validator.validate(contributorDTO);

	    if (contributor == null || contributor.isDocumentMatching(contributorDTO)) {
	        return;
	    }

	    String document = contributorDTO.getPersonType() == PersonType.PERSONAL 
	            ? ValidationUtils.removeSpecialCharacters(contributorDTO.getCpf()) 
	            : ValidationUtils.removeSpecialCharacters(contributorDTO.getCnpj());

	    checkIfDocumentExists(contributorDTO.getPersonType(), document);
	}

	private void checkIfDocumentExists(PersonType personType, String document) {
	    Optional<Contributor> contributorOpt = personType == PersonType.PERSONAL
	            ? repository.findByCpf(document) : repository.findByCnpj(document);

	    contributorOpt.ifPresent(contributor -> {
	        String message = personType == PersonType.PERSONAL ? "The CPF is already in use." : "The CNPJ is already in use.";
	        throw new TaxRegistryException(HttpStatus.BAD_REQUEST, message);
	    });
	}

	@Override
	public void disableContributor(Long id) {
		Optional<Contributor> contributorOpt = repository.findById(id);
		if (contributorOpt.isEmpty()) {
			throw new TaxRegistryException(HttpStatus.NOT_FOUND, "No contributor found.");
		}
		
		Contributor contributor = contributorOpt.get();
		if (contributor.getDisable()) {
			throw new TaxRegistryException(HttpStatus.NOT_FOUND, "Contributor has already been deleted previously.");
		}
		
		contributor.setDisable(true);
		contributor.setDisableAt(LocalDateTime.now());
		repository.save(contributor);
	}

	@Override
	public ContributorDTO findById(Long id) {
		Optional<Contributor> contributorOpt = repository.findByIdEnable(id);
		if (contributorOpt.isEmpty()) {
			throw new TaxRegistryException(HttpStatus.NOT_FOUND, "No contributor found.");
		}
		Contributor contributor = contributorOpt.get();
		return ContributorDTO.fromEntity(contributor);
	}

	@Override
	public ContributorDTO updateContributor(Long id, ContributorDTO contributorDTO) {
		Optional<Contributor> contributorOpt = repository.findByIdEnable(id);
		if (contributorOpt.isEmpty()) {
			throw new TaxRegistryException(HttpStatus.NOT_FOUND, "No contributor found.");
		}
		
		Contributor contributor = contributorOpt.get();
		contributor.setId(id);

		validateContributorDTO(contributorDTO, contributor);
		
		PersonType personType = contributorDTO.getPersonType();
	    if (personType.equals(PersonType.PERSONAL)) {
	        contributor.setPersonalData(createPersonalData(contributorDTO));
	        contributor.setCompanyData(null);
	    } else {
	        contributor.setCompanyData(createCompanyData(contributorDTO));
	        contributor.setPersonalData(null);
	    }

	    contributor.setAddress(createAddress(contributorDTO));
	    contributor.setPersonType(contributorDTO.getPersonType());
	    contributor.setIdentification(contributorDTO.getIdentification());
	    contributor.setPhone(contributorDTO.getPhone());

	    Contributor updatedContributor = repository.save(contributor);
	    return ContributorDTO.fromEntity(updatedContributor);
	}

	private PersonalData createPersonalData(ContributorDTO dto) {
	    return new PersonalData(
	        ValidationUtils.removeSpecialCharacters(dto.getCpf()),
	        dto.getBirthDate(),
	        dto.getGender(),
	        ValidationUtils.removeSpecialCharacters(dto.getRg()),
	        dto.getFatherName(),
	        dto.getMotherName()
	    );
	}

	private CompanyData createCompanyData(ContributorDTO dto) {
	    return new CompanyData(
	        dto.getTradeName(),
	        ValidationUtils.removeSpecialCharacters(dto.getCnpj())
	    );
	}

	private Address createAddress(ContributorDTO dto) {
	    return new Address(
	        dto.getStreet(),
	        dto.getNumber(),
	        dto.getCity(),
	        dto.getState(),
	        dto.getCountry(),
	        dto.getAddition(),
	        dto.getZipCode()
	    );
	}

}
