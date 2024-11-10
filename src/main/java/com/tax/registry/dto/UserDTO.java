package com.tax.registry.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tax.registry.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

	private Long id;
	private String username;
	private String password;

	public UserDTO(User userSaved) {
		this.id = userSaved.getId();
		this.username = userSaved.getUsername();
	}
}
