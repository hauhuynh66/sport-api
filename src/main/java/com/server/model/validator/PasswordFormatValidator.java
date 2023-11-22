package com.server.model.validator;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordFormatValidator implements ConstraintValidator<Password, String> {
    private boolean shouldIncludeNumber = true;
    private boolean shouldIncludeSpecialCharacters = true;

    private String textRegex = ".*([a-zA-Z]+).*";
    private String numberRegex = ".*([0-9])+.*";
    private String specialRegex = ".*([^a-zA-Z0-9]+).*";

    /** 
     * @param constraintAnnotation
     */
    @Override
    public void initialize(Password constraintAnnotation) {
        shouldIncludeNumber = constraintAnnotation.includeNumber();
        shouldIncludeSpecialCharacters = constraintAnnotation.includeSpecialCharacters();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = Pattern.compile(textRegex).matcher(s).matches();
        if (shouldIncludeNumber) {
            result = Pattern.compile(numberRegex).matcher(s).matches();
        }

        if (shouldIncludeSpecialCharacters) {
            result = Pattern.compile(specialRegex).matcher(s).matches();
        }

        return result;
    }
}
