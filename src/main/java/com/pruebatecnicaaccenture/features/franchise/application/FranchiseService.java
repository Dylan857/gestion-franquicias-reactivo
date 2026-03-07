package com.pruebatecnicaaccenture.features.franchise.application;

import org.springframework.stereotype.Service;

import com.pruebatecnicaaccenture.features.franchise.domain.entities.Franchise;
import com.pruebatecnicaaccenture.features.franchise.infrastructure.api.dto.CreateFranchiseDto;
import com.pruebatecnicaaccenture.features.franchise.infrastructure.repository.FranchiseRepository;
import com.pruebatecnicaaccenture.features.shared.exception.CustomException;
import com.pruebatecnicaaccenture.features.shared.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final FranchiseRepository franchiseRepository;

    public Mono<ApiResponse<Franchise>> createFranchise(CreateFranchiseDto dto) {
        return franchiseRepository
                .save(new Franchise(dto.getName()))
                .map(saved -> new ApiResponse<>(201, "0000", "NO ERROR", saved));
    }

    public Mono<ApiResponse<Franchise>> updateFranchise(Long id, CreateFranchiseDto dto) {
        return franchiseRepository.findById(id)
                .flatMap(franchise -> {
                    franchise.setName(dto.getName());
                    return franchiseRepository.save(franchise);
                })
                .map(saved -> new ApiResponse<>(200, "0000", "NO ERROR", saved))
                .switchIfEmpty(Mono.error(new CustomException("Frnquicia no encontrada", 400, "0000", "error")));
    }
}