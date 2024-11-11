package com.tax.registry.modelEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
	
	MALE("Male"),
    FEMALE("Female"),
    UNDEFINED("Undefined");
	
	private String label;

}
