package com.tax.registry.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tax.registry.constructors.UserConstructor;
import com.tax.registry.dto.UserDTO;
import com.tax.registry.exceptions.TaxRegistryException;
import com.tax.registry.model.User;
import com.tax.registry.repositories.UserRepository;
import com.tax.registry.service.implementations.UserServiceImpl;

public class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Mock
	private UserDTO userDTO;
	
	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	void createUserSuccessfully() {
	    when(userDTO.getUsername()).thenReturn("UserTest");
	    when(userDTO.getPassword()).thenReturn("test123");

	    when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

	    String passwordEncoded = "encoded";
	    when(passwordEncoder.encode(userDTO.getPassword())).thenReturn(passwordEncoded);

	    User createUser = UserConstructor.createUser(passwordEncoded);
	    when(userRepository.save(any(User.class))).thenReturn(createUser);

	    UserDTO userCreated = userServiceImpl.createUser(userDTO);

	    assertNotNull(userCreated);
	    verify(userRepository).save(any(User.class));

	    assertNotNull(userCreated.getId());
	    assertNotNull(userCreated.getUsername());
	}
	
	@Test
	void createUserError() {
		when(userDTO.getUsername()).thenReturn("UserTest");
	    when(userDTO.getPassword()).thenReturn("test123");
	
	    when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
	
	    String passwordEncoded = "encoded";
	    when(passwordEncoder.encode(userDTO.getPassword())).thenReturn(passwordEncoded);
	
	    User createUser = UserConstructor.createUser(passwordEncoded);
	    when(userRepository.save(any(User.class))).thenReturn(createUser);
	
	    UserDTO userCreated = userServiceImpl.createUser(userDTO);
	
	    assertNotNull(userCreated);
	    verify(userRepository).save(any(User.class));
	
	    when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(createUser));
	
	    assertThrows(TaxRegistryException.class, () -> userServiceImpl.createUser(userDTO));
	}

}
