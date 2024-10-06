package com.moonboxorg.solidaritirbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ToString
@Validated
public class CreateProductRequestDTO {

    private String name;
    private String description;
    private Long categoryId;
    private String brandName;
    private String manufacturer;
    private String ean13;
}
