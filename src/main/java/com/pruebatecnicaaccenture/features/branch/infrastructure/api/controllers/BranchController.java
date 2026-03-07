package com.pruebatecnicaaccenture.features.branch.infrastructure.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebatecnicaaccenture.features.branch.application.BranchService;
import com.pruebatecnicaaccenture.features.branch.domain.entities.Branch;
import com.pruebatecnicaaccenture.features.branch.domain.entities.BranchFranchise;
import com.pruebatecnicaaccenture.features.branch.infrastructure.api.dto.CreateBranchDto;
import com.pruebatecnicaaccenture.features.branch.infrastructure.api.dto.CreateBranchFranchiseDto;
import com.pruebatecnicaaccenture.features.shared.response.ApiResponse;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping()
    public Mono<ApiResponse<Branch>> create(@Valid @RequestBody CreateBranchDto dto) {
        return branchService.createBranch(dto);
    }

    @PostMapping("/branch-franchise")
    public Mono<ApiResponse<BranchFranchise>> createBranchFranchise(@Valid @RequestBody CreateBranchFranchiseDto dto) {
        return branchService.createBranchFranchise(dto);
    }

    @PutMapping("/{id}")
    public Mono<ApiResponse<Branch>> update(@PathVariable("id") Long id,
            @Valid @RequestBody CreateBranchDto dto) {
        return branchService.updateBranch(id, dto);
    }

}
