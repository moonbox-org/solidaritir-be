package com.moonboxorg.solidaritirbe.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetFilteredItemsInputModel {
    private Long productId;
    private Long categoryId;
    private Long packageId;
    private Integer expInDays;
}
