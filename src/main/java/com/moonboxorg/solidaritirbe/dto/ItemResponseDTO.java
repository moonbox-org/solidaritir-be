package com.moonboxorg.solidaritirbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ItemResponseDTO {
    private Long id;
    private Long productId;
    private Long packageId;
    private LocalDate expirationDate;
}
