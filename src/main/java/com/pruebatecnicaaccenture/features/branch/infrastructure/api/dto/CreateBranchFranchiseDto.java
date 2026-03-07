package com.pruebatecnicaaccenture.features.branch.infrastructure.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBranchFranchiseDto {

    @NotNull(message = "La sucursal es obliogatoria")
    private Long branchId;
    
    @NotNull(message = "La franquicia es obligatoria")
    private Long franchiseId;
}
