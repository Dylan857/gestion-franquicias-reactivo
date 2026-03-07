package com.pruebatecnicaaccenture.features.branch.domain.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("branch_franchises")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BranchFranchise {

    @Id
    private Long id;

    private Long branchId;

    private Long franchiseId;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public BranchFranchise(Long branchId, Long franchiseId) {
        this.branchId = branchId;
        this.franchiseId = franchiseId;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
