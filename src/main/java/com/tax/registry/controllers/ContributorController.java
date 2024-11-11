package com.tax.registry.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tax.registry.dto.ContributorDTO;
import com.tax.registry.service.ContributorService;

@RestController
@RequestMapping("/api/contributors")
public class ContributorController {

	private final ContributorService service;
	
	public ContributorController(ContributorService contributorService) {
		this.service = contributorService;
	}
	
	@GetMapping
	public ResponseEntity<Object> findContributors(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {
		List<ContributorDTO> contributors = service.findContributors(page, size, sortBy, sortOrder);
		return ResponseEntity.status(HttpStatus.OK).body(contributors);
    }
}
