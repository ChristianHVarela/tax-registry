package com.tax.registry.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;

import com.tax.registry.dto.ContributorDTO;
import com.tax.registry.model.embeddables.CompanyData;
import com.tax.registry.model.embeddables.PersonalData;
import com.tax.registry.modelEnum.PersonType;
import com.tax.registry.utils.ValidationUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contributors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contributor implements Serializable {
	
	private static final long serialVersionUID = 8496971551423358382L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String identification;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "person_type", nullable = false, length = 8)
	private PersonType personType;
	
	@Embedded
	private PersonalData personalData = new PersonalData();
	
	@Embedded
	private CompanyData companyData = new CompanyData();
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "fk_address_contributors"))
	private Address address;
	
	@Column(length = 30)
	private String phone;
	
	@Column(name = "create_at", nullable = false)
	private LocalDateTime createAt = LocalDateTime.now();
	
	@Column(name = "disable_at")
	private LocalDateTime disableAt;
	
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean disable = false;
	
	public Contributor(ContributorDTO contributorDTO) {
        this.id = contributorDTO.getId();
        this.identification = contributorDTO.getIdentification();
        this.personType = contributorDTO.getPersonType();
        this.phone = contributorDTO.getPhone();

        this.personalData = new PersonalData(
        	contributorDTO.getPersonType().equals(PersonType.PERSONAL) ? ValidationUtils.removeSpecialCharacters(contributorDTO.getCpf()) : null,
            contributorDTO.getBirthDate(),
            contributorDTO.getGender(),
            contributorDTO.getPersonType().equals(PersonType.PERSONAL) ? ValidationUtils.removeSpecialCharacters(contributorDTO.getRg()) : null,
            contributorDTO.getFatherName(),
            contributorDTO.getMotherName()
        );

        this.companyData = new CompanyData(
            contributorDTO.getTradeName(),
            contributorDTO.getPersonType().equals(PersonType.BUSINESS) ? ValidationUtils.removeSpecialCharacters(contributorDTO.getCnpj()) : null
        );

        this.address = new Address(
            contributorDTO.getStreet(),
            contributorDTO.getNumber(),
            contributorDTO.getCity(),
            contributorDTO.getState(),
            contributorDTO.getCountry(),
            contributorDTO.getAddition(),
            contributorDTO.getZipCode()
        );
    }

}
