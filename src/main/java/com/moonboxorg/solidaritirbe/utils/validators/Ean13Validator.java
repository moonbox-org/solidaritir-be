package com.moonboxorg.solidaritirbe.utils.validators;

import com.moonboxorg.solidaritirbe.utils.BarcodeUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class Ean13Validator implements ConstraintValidator<Ean13, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        return BarcodeUtils.validateEAN13(value);
    }
}
