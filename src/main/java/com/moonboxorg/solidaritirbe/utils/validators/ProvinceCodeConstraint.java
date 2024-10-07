package com.moonboxorg.solidaritirbe.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProvinceCodeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProvinceCodeConstraint {

    String message() default "Invalid province code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
