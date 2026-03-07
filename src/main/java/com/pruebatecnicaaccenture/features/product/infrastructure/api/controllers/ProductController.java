package com.pruebatecnicaaccenture.features.product.infrastructure.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebatecnicaaccenture.features.product.application.ProductService;
import com.pruebatecnicaaccenture.features.product.domain.entities.Product;
import com.pruebatecnicaaccenture.features.product.domain.entities.ProductBranchStock;
import com.pruebatecnicaaccenture.features.product.infrastructure.api.dto.CreateProductBranchStockDto;
import com.pruebatecnicaaccenture.features.product.infrastructure.api.dto.CreateProductDto;
import com.pruebatecnicaaccenture.features.product.infrastructure.api.dto.ModifyStockProductDto;
import com.pruebatecnicaaccenture.features.product.infrastructure.api.dto.ProductBranchTop1Dto;
import com.pruebatecnicaaccenture.features.shared.response.ApiResponse;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping()
    public Mono<ApiResponse<Product>> create(@Valid @RequestBody CreateProductDto dto) {
        return productService.createProduct(dto);
    }

    @GetMapping("/branch-stock/top/{franchiseId}")
    public Mono<ApiResponse<List<ProductBranchTop1Dto>>> getTopProducts(@PathVariable("franchiseId") Long franchiseId) {
        return productService.getTopProductsByFranchise(franchiseId);
    }

    @PostMapping("/branch-stock")
    public Mono<ApiResponse<ProductBranchStock>> createProductBranchStock(
            @Valid @RequestBody CreateProductBranchStockDto dto) {
        return productService.createProductBranchStock(dto);
    }

    @PutMapping("/branch-stock/{productId}/{branchId}")
    public Mono<ApiResponse<ProductBranchStock>> inactivateProductInBranch(@PathVariable("productId") Long productId,
            @PathVariable("branchId") Long branchId) {
        return productService.inactivateProductInBranch(productId, branchId);
    }

    @PutMapping("/branch-stock/{branchId}")
    public Mono<ApiResponse<ProductBranchStock>> modifyStockProduct(@PathVariable("branchId") Long branchId,
            @Valid @RequestBody ModifyStockProductDto dto) {
        return productService.modifyStockProduct(branchId, dto);
    }
}
