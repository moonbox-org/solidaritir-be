package com.moonboxorg.solidaritirbe.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Validated
public class AddItemRequestDTO {
    @NotBlank
    @Min(value = 1, message = "Product ID must be greater than 0")
    private Long productId;
    @Min(value = 1, message = "Package ID must be greater than 0")
    private Long packageId;
    @Future(message = "Expiration date must be in the future")
    private LocalDate expirationDate;
}
