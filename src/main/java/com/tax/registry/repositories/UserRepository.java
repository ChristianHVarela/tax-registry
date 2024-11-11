package com.tax.registry.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.tax.registry.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	@Query(value = "SELECT u FROM User u WHERE u.username = :username")
	UserDetails findByUsernameForDetails(@RequestParam(name = "username") String username);
}
