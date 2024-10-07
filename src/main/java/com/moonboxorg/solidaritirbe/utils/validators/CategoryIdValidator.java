package com.moonboxorg.solidaritirbe.utils.validators;

import com.moonboxorg.solidaritirbe.repositories.CategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryIdValidator implements ConstraintValidator<CategoryId, Long> {

    private final CategoryRepository categoryRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        return categoryRepository.existsById(value);
    }
}
