package com.pruebatecnicaaccenture.features.branch.application;

import org.springframework.stereotype.Service;

import com.pruebatecnicaaccenture.features.branch.domain.entities.Branch;
import com.pruebatecnicaaccenture.features.branch.domain.entities.BranchFranchise;
import com.pruebatecnicaaccenture.features.branch.infrastructure.api.dto.CreateBranchDto;
import com.pruebatecnicaaccenture.features.branch.infrastructure.api.dto.CreateBranchFranchiseDto;
import com.pruebatecnicaaccenture.features.branch.infrastructure.repository.BranchFranchiseRepository;
import com.pruebatecnicaaccenture.features.branch.infrastructure.repository.BranchRepository;
import com.pruebatecnicaaccenture.features.shared.exception.CustomException;
import com.pruebatecnicaaccenture.features.shared.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;

    private final BranchFranchiseRepository branchFranchiseRepository;

    public Mono<ApiResponse<Branch>> findById(Long branchId) {
        return branchRepository.findById(branchId).map(branch -> new ApiResponse<>(200, "0000", "NO ERROR", branch))
                .switchIfEmpty(
                        Mono.error(new CustomException("Sucursal no existe", 400, "0000", "error")));
    }

    public Mono<ApiResponse<Branch>> createBranch(CreateBranchDto dto) {
        return branchRepository
                .save(new Branch(dto.getName()))
                .map(saved -> new ApiResponse<>(201, "0000", "NO ERROR", saved));
    }

    public Mono<ApiResponse<BranchFranchise>> createBranchFranchise(CreateBranchFranchiseDto dto) {
        final Long branchId = dto.getBranchId();
        final Long franchiseId = dto.getFranchiseId();

        return Mono.zip(
                this.findById(branchId),
                this.validateBranchFranchise(branchId, franchiseId))
                .then(branchFranchiseRepository.save(new BranchFranchise(branchId, franchiseId)))
                .map(saved -> new ApiResponse<>(201, "0000", "NO ERROR", saved));
    }

    public Mono<ApiResponse<Branch>> updateBranch(Long id, CreateBranchDto dto) {
        return branchRepository.findById(id)
                .flatMap(branch -> {
                    branch.setName(dto.getName());
                    return branchRepository.save(branch);
                })
                .map(saved -> new ApiResponse<>(200, "0000", "NO ERROR", saved))
                .switchIfEmpty(Mono.error(new CustomException("Sucursal no existe", 400, "0000", "error")));
    }

    private Mono<Void> validateBranchFranchise(Long branchId, Long franchiseId) {
        return branchFranchiseRepository
                .findByBranchIdAndFranchiseIdAndIsActiveTrue(branchId, franchiseId)
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(
                                new CustomException(
                                        "Ya la sucursal está agregada a la franquicia",
                                        400,
                                        "0000",
                                        "error"));
                    }
                    return Mono.empty();
                });
    }
}
