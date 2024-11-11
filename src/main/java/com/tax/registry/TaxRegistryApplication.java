package com.tax.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "TaxRegistry API", version = "1.0", description = "An API for secure registration and management of taxpayer information"))
public class TaxRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxRegistryApplication.class, args);
	}

}
