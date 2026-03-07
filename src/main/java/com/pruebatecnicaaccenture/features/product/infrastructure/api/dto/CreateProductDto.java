package com.pruebatecnicaaccenture.features.product.infrastructure.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {
    
    @NotBlank(message = "El nombre del producto es requerido")
    private String name;
}
