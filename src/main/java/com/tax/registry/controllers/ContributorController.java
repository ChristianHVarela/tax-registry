package com.tax.registry.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tax.registry.dto.ContributorDTO;
import com.tax.registry.service.ContributorService;

@RestController
@RequestMapping("/api/contributors")
public class ContributorController {

	private final ContributorService service;
	
	public ContributorController(ContributorService service) {
		super();
		this.service = service;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable(required = true) Long id) {
		ContributorDTO contributorDTO = service.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(contributorDTO);
	}
	
	@PostMapping
	public ResponseEntity<Object> createContributor(@RequestBody ContributorDTO contributorDTO) {
		ContributorDTO contributorSaved = service.createContributor(contributorDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(contributorSaved);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> disableContributor(@PathVariable(required = true) Long id) {
		service.disableContributor(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateContributor(@PathVariable(required = true) Long id, @RequestBody ContributorDTO contributorDTO) {
		ContributorDTO contributorUpdated = service.updateContributor(id, contributorDTO);
		return ResponseEntity.status(HttpStatus.OK).body(contributorUpdated);
	}
}
