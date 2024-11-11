package com.tax.registry.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tax.registry.dto.ContributorDTO;
import com.tax.registry.exceptions.TaxRegistryException;
import com.tax.registry.modelEnum.PersonType;
import com.tax.registry.utils.ValidationUtils;

@Service
public class ContributorValidator {
	
	public void validate(ContributorDTO contributor) {
	    if (StringUtils.trimToNull(contributor.getIdentification()) == null) {
	        throw new TaxRegistryException(HttpStatus.BAD_REQUEST, "The identification field is required");
	    }

	    if (contributor.getPersonType() == null) {
	        throw new TaxRegistryException(HttpStatus.BAD_REQUEST, "The person type field is required");
	    }

	    if (contributor.getPersonType().equals(PersonType.PERSONAL)) {
	        if (StringUtils.trimToNull(contributor.getCpf()) == null || !ValidationUtils.isCpfValid(contributor.getCpf())) {
	            throw new TaxRegistryException(HttpStatus.BAD_REQUEST, "Invalid CPF");
	        }
	        if (contributor.getGender() == null) {
	            throw new TaxRegistryException(HttpStatus.BAD_REQUEST, "The gender field is required");
	        }
	    }

	    if (contributor.getPersonType().equals(PersonType.BUSINESS)) {
	        if (StringUtils.trimToNull(contributor.getCnpj()) == null || !ValidationUtils.isCnpjValid(contributor.getCnpj())) {
	            throw new TaxRegistryException(HttpStatus.BAD_REQUEST, "Invalid CNPJ");
	        }
	    }

	    if (StringUtils.trimToNull(contributor.getStreet()) == null) {
	        throw new TaxRegistryException(HttpStatus.BAD_REQUEST, "The street field is required");
	    }
	    if (StringUtils.trimToNull(contributor.getCity()) == null) {
	        throw new TaxRegistryException(HttpStatus.BAD_REQUEST, "The city field is required");
	    }
	    if (StringUtils.trimToNull(contributor.getCountry()) == null) {
	        throw new TaxRegistryException(HttpStatus.BAD_REQUEST, "The country field is required");
	    }
	    if (StringUtils.trimToNull(contributor.getZipCode()) == null) {
	        throw new TaxRegistryException(HttpStatus.BAD_REQUEST, "The zip code field is required");
	    }
	}

}
