package com.pruebatecnicaaccenture.features.franchise.domain.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Table("franchises")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Franchise {

    @Id
    private Long id;

    private String name;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public Franchise(String name) {
        this.name = name;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}