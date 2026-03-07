package com.pruebatecnicaaccenture.features.product.domain.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("product_branch_stock")
public class ProductBranchStock {

    @Id
    private Long id;

    private Long productId;

    private Long branchId;

    private Integer stock;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public ProductBranchStock(Long productId, Long branchId, Integer stock) {
        this.productId = productId;
        this.branchId = branchId;
        this.stock = stock;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
