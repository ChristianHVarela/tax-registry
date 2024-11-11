package com.tax.registry.model.embeddables;

import java.time.LocalDate;

import com.tax.registry.modelEnum.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PersonalData {

	@Column(length = 30)
	private String cpf; 
	
	private LocalDate birthDate;
	
	@Column(length = 200)
	@Enumerated(EnumType.STRING)
	private Gender gender;  
	
	@Column(length = 50)
	private String rg;
	
	@Column(length = 200, name = "father_name")
	private String fatherName;
	
	@Column(length = 200, name = "mother_name")
	private String motherName;
}
