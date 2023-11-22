package com.server.model.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeValidator.class)
public @interface Age {
    int min() default 18;
    int max() default 100;
    String message() default "Not a valid age";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
