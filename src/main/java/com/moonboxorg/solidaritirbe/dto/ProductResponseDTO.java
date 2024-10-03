package com.moonboxorg.solidaritirbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private String categoryName;
    private String brandName;
    private String manufacturer;
    private boolean active;
    private String ean13;
}
