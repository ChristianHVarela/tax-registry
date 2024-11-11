package com.tax.registry.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tax.registry.model.Contributor;

@Repository
public interface ContributorRepository extends JpaRepository<Contributor, Long> {
	
	@Query(value = "SELECT c FROM Contributor c WHERE c.personalData.cpf = ?1 AND c.disable IS FALSE")
	Optional<Contributor> findByCpf(String cpf);
	
	@Query(value = "SELECT c FROM Contributor c WHERE c.companyData.cnpj = ?1 AND c.disable IS FALSE")
	Optional<Contributor> findByCnpj(String cnpj);

	@Query(value = "SELECT c FROM Contributor c WHERE c.disable IS FALSE")
	Page<Contributor> findAllEnable(PageRequest pageRequest);

	@Query(value = "SELECT c FROM Contributor c WHERE c.disable IS FALSE AND c.id = ?1")
	Optional<Contributor> findByIdEnable(Long id);
}
