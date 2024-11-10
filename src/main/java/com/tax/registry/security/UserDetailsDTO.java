package com.tax.registry.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.tax.registry.model.User;

import lombok.Getter;

@Getter
public class UserDetailsDTO {

	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	private UserDetailsDTO(User user){
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authorities = new ArrayList<>();
    }

    public static UserDetailsDTO create(User user){
        return new UserDetailsDTO(user);
    }
}
