package com.moonboxorg.solidaritirbe.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ContainerTypeIdValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContainerTypeId {

    String message() default "Invalid container type ID";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
