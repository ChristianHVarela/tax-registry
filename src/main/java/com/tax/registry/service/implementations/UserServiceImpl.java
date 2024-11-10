package com.tax.registry.service.implementations;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tax.registry.dto.UserDTO;
import com.tax.registry.exceptions.TaxRegistryException;
import com.tax.registry.model.User;
import com.tax.registry.repositories.UserRepository;
import com.tax.registry.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
	    this.userRepository = userRepository;
	    this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		Optional<User> userOpt = userRepository.findByUsername(userDTO.getUsername());

        if(userOpt.isPresent()){
            throw new TaxRegistryException(HttpStatus.BAD_REQUEST, "A user with this username already exists");
        }

        String passwordEncoded = passwordEncoder.encode(userDTO.getPassword());
        User user = new User(userDTO.getUsername(), passwordEncoded);
        User userSaved = userRepository.save(user);
        
		return new UserDTO(userSaved);
	}
}
