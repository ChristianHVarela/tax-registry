package com.tax.registry.constructors;

import com.tax.registry.dto.UserDTO;
import com.tax.registry.model.User;

public class UserConstructor {
	
	public static User createUser(String password) {
		return new User(1l, "UserTest", password);
	}

	public static UserDTO createUserDTOWithPassword() {
		return new UserDTO(null, "UserTest", "test123");
	}
	
	public static UserDTO createUserDTOWithoutPassword() {
		return new UserDTO(1l, "UserTest", null);
	}
}
