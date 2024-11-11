package com.tax.registry.dto;

import com.tax.registry.model.Contributor;

public record ContributorDTO(Long id, String identification) {

	public ContributorDTO(Contributor contributor) {
		this(contributor.getId(), contributor.getIdentification());
	}
}
