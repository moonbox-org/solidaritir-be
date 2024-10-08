package com.moonboxorg.solidaritirbe.dto;

import com.moonboxorg.solidaritirbe.utils.validators.CategoryId;
import com.moonboxorg.solidaritirbe.utils.validators.ContainerTypeId;
import com.moonboxorg.solidaritirbe.utils.validators.Ean13;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ToString
@Validated
public class AddProductRequestDTO {

    @NotBlank
    private String name;
    private String description;
    @CategoryId
    private Long categoryId;
    private String brandName;
    private String manufacturer;
    private boolean active;
    @ContainerTypeId
    private Long containerTypeId;
    @Ean13
    private String ean13;
}
