package com.moonboxorg.solidaritirbe.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ProvinceResponseDTO(
        String code,
        String name,
        String region,
        List<Long> collectionPointCodes,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime lastUpdatedAt,
        String lastUpdatedBy
) {
}
