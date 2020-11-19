package com.andrei.ds.DTOs.validators;

import com.andrei.ds.DTOs.validators.annotations.AgeLimit;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class AgeValidator implements ConstraintValidator<AgeLimit, Integer> {

    private int ageLimit;

    @Override
    public void initialize(AgeLimit constraintAnnotation) {
        this.ageLimit = constraintAnnotation.limit();
    }

    @Override
    public boolean isValid(Integer inputAge, ConstraintValidatorContext constraintValidatorContext) {
        return inputAge > ageLimit;
    }


}
