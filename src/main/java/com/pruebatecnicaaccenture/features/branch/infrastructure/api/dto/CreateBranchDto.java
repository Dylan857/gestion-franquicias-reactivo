package com.pruebatecnicaaccenture.features.branch.infrastructure.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBranchDto {

    @NotBlank(message = "El nombre de la sucursal es requerido")
    private String name;
}