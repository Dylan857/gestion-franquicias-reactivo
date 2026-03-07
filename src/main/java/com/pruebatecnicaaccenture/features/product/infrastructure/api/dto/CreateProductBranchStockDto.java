package com.pruebatecnicaaccenture.features.product.infrastructure.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductBranchStockDto {
    @NotNull(message = "El producto es requerido")
    private Long productId;

    @NotNull(message = "La sucursal es requerida")
    private Long branchId;

    @NotNull(message = "El stock es requerido")
    private Integer stock;
}
