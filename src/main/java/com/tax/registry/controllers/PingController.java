package com.tax.registry.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController
@RequestMapping("/ping")
public class PingController {

	@GetMapping
	public ResponseEntity<Object> ping(@RequestParam(name = "message") String message) {
		return ResponseEntity.ok("Ping -> " + message);
	}
}
