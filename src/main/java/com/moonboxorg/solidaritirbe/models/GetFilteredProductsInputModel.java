package com.moonboxorg.solidaritirbe.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetFilteredProductsInputModel {
    private String name;
    private Long categoryId;
    private boolean active;
}
