package com.pruebatecnicaaccenture.features.branch.infrastructure.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.pruebatecnicaaccenture.features.branch.domain.entities.Branch;

@Repository
public interface BranchRepository extends R2dbcRepository<Branch, Long>{
    
}
