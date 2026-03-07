package com.pruebatecnicaaccenture.features.product.infrastructure.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.pruebatecnicaaccenture.features.product.domain.entities.Product;

public interface ProductRepository extends R2dbcRepository<Product, Long> {
    
}
