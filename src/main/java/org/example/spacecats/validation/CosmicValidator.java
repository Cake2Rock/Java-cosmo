package org.example.spacecats.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CosmicValidator implements ConstraintValidator<CosmicValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        String lowercaseValue = value.toLowerCase();
        return lowercaseValue.contains("star") || lowercaseValue.contains("galaxy") || lowercaseValue.contains("comet");
    }
}
