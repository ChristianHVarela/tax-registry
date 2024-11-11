package com.tax.registry.service;

import java.util.List;

import com.tax.registry.dto.ContributorDTO;

public interface ContributorService {

	List<ContributorDTO> findContributors(Integer page, Integer size, String sortBy, String sortOrder);

	ContributorDTO createContributor(ContributorDTO contributorDTO);
	
}
