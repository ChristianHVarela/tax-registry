package com.tax.registry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tax.registry.model.Contributor;

@Repository
public interface ContributorRepository extends JpaRepository<Contributor, Long> {

}
