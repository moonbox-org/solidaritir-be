package com.moonboxorg.solidaritirbe.dto;

import com.moonboxorg.solidaritirbe.utils.validators.ProvinceCodeConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ToString
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class CreateCollectionPointRequestDTO {

    @NotBlank(message = "collection point name cannot be empty")
    private String name;

    @ProvinceCodeConstraint
    @NotBlank(message = "collection point province code cannot be empty")
    private String provinceCode;

    @NotNull(message = "collection point active cannot be null")
    private boolean active;

    private String notes;

}
