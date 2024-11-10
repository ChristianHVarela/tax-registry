package com.tax.registry.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tax.registry.dto.UserDTO;
import com.tax.registry.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService service;
	
	public UserController(UserService userService) {
		this.service = userService;
	}
	
	@PostMapping
	public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO) {
		UserDTO createUser = service.createUser(userDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
	}
	
}
