package com.pruebatecnicaaccenture.features.branch.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pruebatecnicaaccenture.features.branch.domain.entities.Branch;
import com.pruebatecnicaaccenture.features.branch.domain.entities.BranchFranchise;
import com.pruebatecnicaaccenture.features.branch.infrastructure.api.dto.CreateBranchFranchiseDto;
import com.pruebatecnicaaccenture.features.branch.infrastructure.repository.BranchFranchiseRepository;
import com.pruebatecnicaaccenture.features.branch.infrastructure.repository.BranchRepository;
import com.pruebatecnicaaccenture.features.shared.response.ApiResponse;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private BranchFranchiseRepository branchFranchiseRepository;

    @InjectMocks
    private BranchService branchService;

    @Test
    void shouldAssociateBranchWithFranchiseSuccessfully() {
        Long branchId = 1L;
        Long franchiseId = 2L;

        CreateBranchFranchiseDto dto = new CreateBranchFranchiseDto(branchId, franchiseId);

        when(branchRepository.findById(branchId)).thenReturn(Mono.just(new Branch()));

        when(branchFranchiseRepository.findByBranchIdAndFranchiseIdAndIsActiveTrue(branchId, franchiseId))
                .thenReturn(Mono.empty());

        BranchFranchise savedRelation = new BranchFranchise();
        savedRelation.setBranchId(branchId);
        savedRelation.setFranchiseId(franchiseId);

        when(branchFranchiseRepository.save(any(BranchFranchise.class)))
                .thenReturn(Mono.just(savedRelation));

        Mono<ApiResponse<BranchFranchise>> result = branchService.createBranchFranchise(dto);
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertEquals(201, response.getStatusCode());
                    assertEquals(branchId, response.getData().getBranchId());
                })
                .verifyComplete();
    }
}
