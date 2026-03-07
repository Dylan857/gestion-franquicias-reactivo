package com.pruebatecnicaaccenture.features.franchise.infrastructure.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.pruebatecnicaaccenture.features.franchise.domain.entities.Franchise;

@Repository
public interface FranchiseRepository extends R2dbcRepository<Franchise, Long> {
}