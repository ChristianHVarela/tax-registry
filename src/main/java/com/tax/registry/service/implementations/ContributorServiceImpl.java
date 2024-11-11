package com.tax.registry.service.implementations;

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
import com.tax.registry.model.Contributor;
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
		validator.validate(contributorDTO);
		
		if (contributorDTO.getPersonType().equals(PersonType.PERSONAL)) {
			Optional<Contributor> contributorOpt = repository.findByCpf(ValidationUtils.removeSpecialCharacters(contributorDTO.getCpf()));
			if (contributorOpt.isPresent()) {
				throw new TaxRegistryException(HttpStatus.BAD_REQUEST, "The CPF is already in use.");
			}
		} else {
			Optional<Contributor> contributorOpt = repository.findByCnpj(ValidationUtils.removeSpecialCharacters(contributorDTO.getCnpj()));
			if (contributorOpt.isPresent()) {
				throw new TaxRegistryException(HttpStatus.BAD_REQUEST, "The CNPJ is already in use.");
			}
		}
		
		Contributor contributor = new Contributor(contributorDTO);
		contributor = repository.save(contributor);
		
		return ContributorDTO.fromEntity(contributor);
	}

}
