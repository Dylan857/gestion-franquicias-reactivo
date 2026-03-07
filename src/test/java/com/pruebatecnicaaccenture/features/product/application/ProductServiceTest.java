package com.pruebatecnicaaccenture.features.product.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pruebatecnicaaccenture.features.branch.application.BranchService;
import com.pruebatecnicaaccenture.features.branch.domain.entities.Branch;
import com.pruebatecnicaaccenture.features.product.domain.entities.Product;
import com.pruebatecnicaaccenture.features.product.domain.entities.ProductBranchStock;
import com.pruebatecnicaaccenture.features.product.infrastructure.api.dto.CreateProductBranchStockDto;
import com.pruebatecnicaaccenture.features.product.infrastructure.api.dto.ModifyStockProductDto;
import com.pruebatecnicaaccenture.features.product.infrastructure.api.dto.ProductBranchTop1Dto;
import com.pruebatecnicaaccenture.features.product.infrastructure.repository.ProductBranchStockRepository;
import com.pruebatecnicaaccenture.features.product.infrastructure.repository.ProductRepository;
import com.pruebatecnicaaccenture.features.shared.response.ApiResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductBranchStockRepository productBranchStockRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BranchService branchService;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProductBranchStockWhenValidationsPass() {
        Long productId = 1L;
        Long branchId = 2L;
        CreateProductBranchStockDto dto = new CreateProductBranchStockDto(productId, branchId, 50);

        Product fakeProduct = new Product();
        fakeProduct.setId(productId);
        fakeProduct.setName("Producto de Prueba");
        fakeProduct.setIsActive(true);

        when(productRepository.findById(productId)).thenReturn(Mono.just(fakeProduct));

        Branch fakeBranch = new Branch();
        fakeBranch.setId(branchId);
        ApiResponse<Branch> mockBranchResponse = new ApiResponse<>(200, "0000", "NO ERROR", fakeBranch);
        when(branchService.findById(branchId)).thenReturn(Mono.just(mockBranchResponse));

        when(productBranchStockRepository.findByProductIdAndBranchIdAndIsActiveTrue(productId, branchId))
                .thenReturn(Mono.empty());

        ProductBranchStock entityToSave = new ProductBranchStock(productId, branchId, 50);
        when(productBranchStockRepository.save(any(ProductBranchStock.class)))
                .thenReturn(Mono.just(entityToSave));

        Mono<ApiResponse<ProductBranchStock>> result = productService.createProductBranchStock(dto);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertEquals(201, response.getStatusCode());
                    assertEquals(50, response.getData().getStock());
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnTopProductsByFranchiseSuccessfully() {
        Long franchiseId = 1L;
        ProductBranchTop1Dto fakeProduct = new ProductBranchTop1Dto("Coca Cola", 100, "Sucursal Centro");

        when(productBranchStockRepository.findTopProductsByFranchise(franchiseId))
                .thenReturn(Flux.just(fakeProduct));

        Mono<ApiResponse<List<ProductBranchTop1Dto>>> result = productService.getTopProductsByFranchise(franchiseId);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertEquals(200, response.getStatusCode());
                    assertFalse(response.getData().isEmpty());
                    assertEquals("Coca Cola", response.getData().get(0).productName());
                })
                .verifyComplete();
    }

    @Test
    void shouldModifyStockSuccessfully() {
        Long productId = 1L;
        Long branchId = 2L;
        Integer newStock = 100;
        ModifyStockProductDto dto = new ModifyStockProductDto(productId, newStock);

        Product fakeProduct = new Product();
        fakeProduct.setId(productId);
        fakeProduct.setIsActive(true);

        when(productRepository.findById(productId))
                .thenReturn(Mono.just(fakeProduct));

        Branch fakeBranch = new Branch();
        ApiResponse<Branch> branchRes = new ApiResponse<>(200, "0000", "NO ERROR", fakeBranch);
        when(branchService.findById(branchId)).thenReturn(Mono.just(branchRes));

        ProductBranchStock stockEntity = new ProductBranchStock(productId, branchId, 50);
        when(productBranchStockRepository.findByProductIdAndBranchIdAndIsActiveTrue(productId, branchId))
                .thenReturn(Mono.just(stockEntity));
        when(productBranchStockRepository.save(any(ProductBranchStock.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        Mono<ApiResponse<ProductBranchStock>> result = productService.modifyStockProduct(branchId, dto);
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertNotNull(response.getData());
                    assertEquals(200, response.getStatusCode());
                    assertEquals(newStock, response.getData().getStock());
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnInactiveProductBranchStockSuccessfully() {
        Long productId = 1L;
        Long branchId = 2L;

        Product fakeProduct = new Product();
        fakeProduct.setId(productId);
        fakeProduct.setIsActive(true);

        when(productRepository.findById(productId))
                .thenReturn(Mono.just(fakeProduct));

        Branch fakeBranch = new Branch();
        ApiResponse<Branch> branchRes = new ApiResponse<>(200, "0000", "NO ERROR", fakeBranch);
        when(branchService.findById(branchId)).thenReturn(Mono.just(branchRes));

        ProductBranchStock stockEntity = new ProductBranchStock(productId, branchId, 50);
        stockEntity.setIsActive(true);
        when(productBranchStockRepository.findByProductIdAndBranchIdAndIsActiveTrue(productId, branchId))
                .thenReturn(Mono.just(stockEntity));
        when(productBranchStockRepository.save(any(ProductBranchStock.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        Mono<ApiResponse<ProductBranchStock>> result = productService.inactivateProductInBranch(productId, branchId);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertNotNull(response.getData());
                    assertEquals(200, response.getStatusCode());
                    assertEquals(false, response.getData().getIsActive());
                })
                .verifyComplete();
    }
}