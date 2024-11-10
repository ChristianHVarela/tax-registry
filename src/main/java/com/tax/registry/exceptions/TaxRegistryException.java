package com.tax.registry.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class TaxRegistryException extends RuntimeException {
	
	private static final long serialVersionUID = 8495486991461236598L;
	private final HttpStatus statusCode;
	
	public TaxRegistryException(HttpStatus statuscode, String message) {
		super(message);
		this.statusCode = statuscode;
	}

}
