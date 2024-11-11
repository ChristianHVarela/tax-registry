package com.tax.registry.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/contributors", produces = {"application/json"})
@Tag(name = "Contributors")
public class ContributorController {

    private final ContributorService service;

    public ContributorController(ContributorService service) {
        super();
        this.service = service;
    }

    @Operation(summary = "Search for a list of Contributors", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contributor search completed successfully."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    @GetMapping
    public ResponseEntity<List<ContributorDTO>> findContributors(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        List<ContributorDTO> contributors = service.findContributors(page, size, sortBy, sortOrder);
        return ResponseEntity.ok(contributors);
    }

    @Operation(summary = "Search for a specific Contributor", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contributor found."),
            @ApiResponse(responseCode = "404", description = "No contributor found."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ContributorDTO> findById(@PathVariable Long id) {
        ContributorDTO contributorDTO = service.findById(id);
        return ResponseEntity.ok(contributorDTO);
    }

    @Operation(summary = "Create a Contributor", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contributor created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid parameters."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContributorDTO> createContributor(@RequestBody ContributorDTO contributorDTO) {
        ContributorDTO contributorSaved = service.createContributor(contributorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(contributorSaved);
    }

    @Operation(summary = "Delete a Contributor", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contributor deleted successfully."),
            @ApiResponse(responseCode = "404", description = "No contributor found."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableContributor(@PathVariable Long id) {
        service.disableContributor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Update a Contributor", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contributor updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid parameters."),
            @ApiResponse(responseCode = "404", description = "Contributor not found."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred."),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ContributorDTO> updateContributor(@PathVariable Long id, @RequestBody ContributorDTO contributorDTO) {
        ContributorDTO contributorUpdated = service.updateContributor(id, contributorDTO);
        return ResponseEntity.ok(contributorUpdated);
    }
}
