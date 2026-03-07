package com.pruebatecnicaaccenture.features.franchise.infrastructure.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateFranchiseDto {

    @NotNull(message = "El nombre es requerido")
    private String name;
}