package com.server.model.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordFormatValidator.class)
public @interface Password {
    boolean includeNumber() default true;
    boolean includeSpecialCharacters() default true;
    String message() default "Password format is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
