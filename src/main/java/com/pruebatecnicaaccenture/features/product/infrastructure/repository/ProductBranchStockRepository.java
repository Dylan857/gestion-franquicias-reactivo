package com.pruebatecnicaaccenture.features.product.infrastructure.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.pruebatecnicaaccenture.features.product.domain.entities.ProductBranchStock;
import com.pruebatecnicaaccenture.features.product.infrastructure.api.dto.ProductBranchTop1Dto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductBranchStockRepository extends R2dbcRepository<ProductBranchStock, Long> {
    Mono<ProductBranchStock> findByProductIdAndBranchIdAndIsActiveTrue(Long productId, Long branchId);

    @Query("SELECT DISTINCT ON (b.id) " +
            "p.name AS product_name, pbs.stock, b.name AS branch_name " +
            "FROM product_branch_stock pbs " +
            "JOIN products p ON pbs.product_id = p.id " +
            "JOIN branches b ON pbs.branch_id = b.id " +
            "JOIN branch_franchises bf ON b.id = bf.branch_id " + // <--- Relación con Franquicia
            "WHERE pbs.is_active = true AND bf.franchise_id = :franchiseId " + // <--- Filtro puntual
            "ORDER BY b.id, pbs.stock DESC")
    Flux<ProductBranchTop1Dto> findTopProductsByFranchise(Long franchiseId);
}
