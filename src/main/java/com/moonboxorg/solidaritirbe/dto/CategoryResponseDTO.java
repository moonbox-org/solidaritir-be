package com.moonboxorg.solidaritirbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private Long parentCategoryId;
    private Set<CategoryResponseDTO> subCategories;
}
