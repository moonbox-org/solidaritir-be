package com.moonboxorg.solidaritirbe.utils.validators;

import com.moonboxorg.solidaritirbe.repositories.ProvinceRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProvinceCodeValidator implements ConstraintValidator<ProvinceCodeConstraint, String> {

    private final ProvinceRepository provinceRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return provinceRepository.existsByCode(value);
    }
}
