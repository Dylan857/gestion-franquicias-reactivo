package com.pruebatecnicaaccenture.features.branch.infrastructure.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.pruebatecnicaaccenture.features.branch.domain.entities.BranchFranchise;

import reactor.core.publisher.Mono;

@Repository
public interface BranchFranchiseRepository extends R2dbcRepository<BranchFranchise, Long> {
    Mono<BranchFranchise> findByBranchIdAndFranchiseIdAndIsActiveTrue(Long branchId, Long franchiseId);
}
