package com.tax.registry.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {
	
	private static final long serialVersionUID = -8410831848015926399L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String street;
	
	private String number;
	
	@Column(nullable = false)
	private String city;
	
	@Column(nullable = false)
	private String state;
	
	@Column(nullable = false)
	private String country;
	
	private String addition;
	
	@Column(nullable = false)
	private String zipCode;

	public Address(String street, String number, String city, String state, String country, String addition, String zipCode) {
		super();
		this.street = street;
		this.number = number;
		this.city = city;
		this.state = state;
		this.country = country;
		this.addition = addition;
		this.zipCode = zipCode;
	}
}
