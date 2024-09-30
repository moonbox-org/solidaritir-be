package com.moonboxorg.solidaritirbe.dto;

import java.time.LocalDateTime;

public record CollectionPointResponseDTO(
        Long code,
        String name,
        String provinceCode,
        String provinceName,
        String region,
        boolean active,
        String notes,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime lastUpdatedAt,
        String lastUpdatedBy
) {
}
