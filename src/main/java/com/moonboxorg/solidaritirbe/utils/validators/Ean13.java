package com.moonboxorg.solidaritirbe.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = Ean13Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Ean13 {

    String message() default "Invalid EAN13 code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
