package com.server.document.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NFLScoreValidator.class)
public @interface NFLScore {
    String message() default "Not a valid score";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
