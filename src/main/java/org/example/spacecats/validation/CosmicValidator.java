package org.example.spacecats.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CosmicValidator implements ConstraintValidator<CosmicValidation, String> {

    @Override
    public void initialize(CosmicValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return value.contains("star") || value.contains("galaxy") || value.contains("comet");
    }
}
