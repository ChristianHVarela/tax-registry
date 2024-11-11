package com.tax.registry.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tax.registry.model.Address;
import com.tax.registry.model.Contributor;
import com.tax.registry.model.embeddables.CompanyData;
import com.tax.registry.model.embeddables.PersonalData;
import com.tax.registry.modelEnum.Gender;
import com.tax.registry.modelEnum.PersonType;
import com.tax.registry.utils.ValidationUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContributorDTO {

	@JsonInclude(JsonInclude.Include.NON_NULL) 
    private Long id;
	
    private String identification;
    private PersonType personType;
    private String cpf;
    private LocalDate birthDate;
    private Gender gender;
    private String rg;
    private String fatherName;
    private String motherName;
    private String tradeName;
    private String cnpj;
    private String street;
    private String number;
    private String city;
    private String state;
    private String country;
    private String addition;
    private String zipCode;
    private String phone;

    public static ContributorDTO fromEntity(Contributor contributor) {
        PersonalData personalData = contributor.getPersonalData();
        CompanyData companyData = contributor.getCompanyData();
        Address address = contributor.getAddress();
        PersonType personType = contributor.getPersonType();

        return new ContributorDTO(
            contributor.getId(),
            contributor.getIdentification(),
            contributor.getPersonType(),
            personType.equals(PersonType.PERSONAL) ? ValidationUtils.addMask(personalData.getCpf()) : null,
            personType.equals(PersonType.PERSONAL) ? personalData.getBirthDate() : null,
            personType.equals(PersonType.PERSONAL) ? personalData.getGender() : null,
            personType.equals(PersonType.PERSONAL) ? personalData.getRg() : null,
            personType.equals(PersonType.PERSONAL) ? personalData.getFatherName() : null,
            personType.equals(PersonType.PERSONAL) ? personalData.getMotherName() : null,
            personType.equals(PersonType.BUSINESS) ? companyData.getTradeName() : null,
            personType.equals(PersonType.BUSINESS) ? ValidationUtils.addMask(companyData.getCnpj()) : null,
            address != null ? address.getStreet() : null,
            address != null ? address.getNumber() : null,
            address != null ? address.getCity() : null,
            address != null ? address.getState() : null,
            address != null ? address.getCountry() : null,
            address != null ? address.getAddition() : null,
            address != null ? address.getZipCode() : null,
            contributor.getPhone()
        );
    }
}
