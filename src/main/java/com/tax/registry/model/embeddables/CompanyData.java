package com.tax.registry.model.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CompanyData {

	@Column(name = "trade_name")
	private String tradeName;

	@Column(length = 20)
	private String cnpj;
}
