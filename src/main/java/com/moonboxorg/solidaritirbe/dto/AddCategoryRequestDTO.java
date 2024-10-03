package com.moonboxorg.solidaritirbe.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ToString
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class AddCategoryRequestDTO {

    private Long parentId;

    @NotBlank
    private String name;

    private String description;
}
