package com.moonboxorg.solidaritirbe.utils.validators;

import com.moonboxorg.solidaritirbe.repositories.ContainerTypeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContainerTypeIdValidator implements ConstraintValidator<ContainerTypeId, Long> {

    private final ContainerTypeRepository containerTypeRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        return containerTypeRepository.existsById(value);
    }
}
