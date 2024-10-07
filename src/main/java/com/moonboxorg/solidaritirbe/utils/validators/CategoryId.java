package com.moonboxorg.solidaritirbe.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CategoryIdValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryId {

    String message() default "Invalid category ID";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
