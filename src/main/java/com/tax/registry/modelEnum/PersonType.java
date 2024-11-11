package com.tax.registry.modelEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PersonType {
	
	PERSONAL("Personal"), BUSINESS("Business");
	
	private String label;

}
