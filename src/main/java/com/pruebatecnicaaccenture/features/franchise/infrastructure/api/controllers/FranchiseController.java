package com.pruebatecnicaaccenture.features.franchise.infrastructure.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebatecnicaaccenture.features.franchise.application.FranchiseService;
import com.pruebatecnicaaccenture.features.franchise.domain.entities.Franchise;
import com.pruebatecnicaaccenture.features.franchise.infrastructure.api.dto.CreateFranchiseDto;
import com.pruebatecnicaaccenture.features.shared.response.ApiResponse;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franchise")
public class FranchiseController {

    @Autowired
    private FranchiseService franchiseService;

    @PostMapping()
    public Mono<ApiResponse<Franchise>> create(@Valid @RequestBody CreateFranchiseDto dto) {
        return franchiseService.createFranchise(dto);
    }

    @PutMapping("/{id}")
    public Mono<ApiResponse<Franchise>> update(@PathVariable("id") Long id,
            @Valid @RequestBody CreateFranchiseDto dto) {
        return franchiseService.updateFranchise(id, dto);
    }
}