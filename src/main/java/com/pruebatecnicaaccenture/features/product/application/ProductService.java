package com.pruebatecnicaaccenture.features.product.application;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.pruebatecnicaaccenture.features.branch.application.BranchService;
import com.pruebatecnicaaccenture.features.product.domain.entities.Product;
import com.pruebatecnicaaccenture.features.product.domain.entities.ProductBranchStock;
import com.pruebatecnicaaccenture.features.product.infrastructure.api.dto.CreateProductBranchStockDto;
import com.pruebatecnicaaccenture.features.product.infrastructure.api.dto.CreateProductDto;
import com.pruebatecnicaaccenture.features.product.infrastructure.api.dto.ModifyStockProductDto;
import com.pruebatecnicaaccenture.features.product.infrastructure.api.dto.ProductBranchTop1Dto;
import com.pruebatecnicaaccenture.features.product.infrastructure.repository.ProductBranchStockRepository;
import com.pruebatecnicaaccenture.features.product.infrastructure.repository.ProductRepository;
import com.pruebatecnicaaccenture.features.shared.exception.CustomException;
import com.pruebatecnicaaccenture.features.shared.response.ApiResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final BranchService branchService;

    private final ProductRepository productRepository;

    private final ProductBranchStockRepository productBranchStockRepository;

    public Mono<ApiResponse<Product>> createProduct(CreateProductDto dto) {
        return productRepository
                .save(new Product(dto.getName()))
                .map(saved -> new ApiResponse<>(201, "0000", "NO ERROR", saved));
    }

    public Mono<ApiResponse<Product>> updateProduct(Long id, CreateProductDto dto) {
        return productRepository.findById(id)
                .flatMap(product -> {
                    product.setName(dto.getName());
                    return productRepository.save(product);
                })
                .map(saved -> new ApiResponse<>(200, "0000", "NO ERROR", saved))
                .switchIfEmpty(Mono.error(new CustomException("Producto no existe", 400, "0000", "error")));
    }

    public Mono<ApiResponse<List<ProductBranchTop1Dto>>> getTopProductsByFranchise(Long franchiseId) {
        return productBranchStockRepository.findTopProductsByFranchise(franchiseId)
                .collectList()
                .map(list -> new ApiResponse<>(200, "0000", "NO ERROR", list));
    }

    public Mono<ApiResponse<ProductBranchStock>> createProductBranchStock(CreateProductBranchStockDto dto) {
        Long productId = dto.getProductId();
        Long branchId = dto.getBranchId();

        ProductBranchStock entity = new ProductBranchStock(
                productId,
                branchId,
                dto.getStock());

        return Mono.zip(
                validateIfProductExists(productId),
                branchService.findById(branchId),
                this.validateIfProductAssociatedWithBranch(productId, branchId))
                .then(productBranchStockRepository.save(entity))
                .map(saved -> new ApiResponse<>(201, "0000", "NO ERROR", saved));
    }

    public Mono<ApiResponse<ProductBranchStock>> inactivateProductInBranch(Long productId, Long branchId) {
        return Mono.zip(validateIfProductExists(productId),
                branchService.findById(branchId))
                .then(productBranchStockRepository.findByProductIdAndBranchIdAndIsActiveTrue(productId, branchId))
                .map(productBranchStock -> {
                    productBranchStock.setIsActive(false);
                    productBranchStock.setDeletedAt(LocalDateTime.now());
                    return productBranchStock;
                })
                .flatMap(productBranchStockRepository::save)
                .map(saved -> new ApiResponse<ProductBranchStock>(200, "0000", "NO ERROR", saved))
                .switchIfEmpty(Mono.error(new CustomException(404, "ERROR", "Stock no encontrado")));
    }

    public Mono<ApiResponse<ProductBranchStock>> modifyStockProduct(Long branchId, ModifyStockProductDto dto) {
        return Mono.zip(validateIfProductExists(dto.getProductId()),
                branchService.findById(branchId))
                .then(productBranchStockRepository.findByProductIdAndBranchIdAndIsActiveTrue(dto.getProductId(),
                        branchId))
                .map(product -> {
                    product.setStock(dto.getStock());
                    return product;
                })
                .flatMap(productBranchStockRepository::save)
                .map(saved -> new ApiResponse<ProductBranchStock>(200, "0000", "NO ERROR", saved))
                .switchIfEmpty(Mono.error(new CustomException(404, "ERROR", "Stock no encontrado")));
    }

    private Mono<Void> validateIfProductExists(Long productId) {
        return productRepository
                .findById(productId).switchIfEmpty(
                        Mono.error(new CustomException(
                                "Producto no existe",
                                404,
                                "0000", "error")))
                .flatMap(product -> {
                    if (!product.getIsActive()) {
                        return Mono.error(
                                new CustomException(
                                        "Producto no esta activo",
                                        400, "0000", "error"));
                    }
                    return Mono.empty();
                });
    }

    private Mono<Void> validateIfProductAssociatedWithBranch(Long productId, Long branchId) {
        return productBranchStockRepository.findByProductIdAndBranchIdAndIsActiveTrue(productId, branchId).hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(
                                new CustomException(
                                        "Ya el producto esta agregado a la sucursal",
                                        400,
                                        "0000",
                                        "error"));
                    }
                    return Mono.empty();
                });
    }
}
