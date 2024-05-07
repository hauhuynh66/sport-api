package com.server.document.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NFLScoreValidator implements ConstraintValidator<NFLScore, Integer> {

    @Override
    public boolean isValid(Integer score, ConstraintValidatorContext context) {
        if(score < 0) {
            return false;
        }

        if(score % 3 == 0 || score % 6 == 0 || score % 7 == 0 || score % 8 == 0) {
            return true;
        }

        return false;
    }
    
}
