package com.tax.registry.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tax.registry.dto.LoginDTO;
import com.tax.registry.dto.TokenResponseDTO;
import com.tax.registry.dto.UserDTO;
import com.tax.registry.model.User;
import com.tax.registry.security.TokenConfiguration;
import com.tax.registry.service.UserService;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;

@Hidden
@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService service;
	private final AuthenticationManager authenticationManager;
	private final TokenConfiguration tokenConfiguration;
	
	public UserController(UserService service, AuthenticationManager authenticationManager,
			TokenConfiguration tokenConfiguration) {
		super();
		this.service = service;
		this.authenticationManager = authenticationManager;
		this.tokenConfiguration = tokenConfiguration;
	}

	@PostMapping
	public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO) {
		UserDTO createUser = service.createUser(userDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
	}
	
	@PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDTO data) throws Exception{
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(data.username(), data.password());
	    Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

	    String token = tokenConfiguration.generateToken((User) authentication.getPrincipal());
	    return ResponseEntity.ok(new TokenResponseDTO(token));
    }
	
}
