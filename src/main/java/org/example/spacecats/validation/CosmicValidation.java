package org.example.spacecats.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CosmicValidator.class)

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CosmicValidation {
    String message() default "Value must contain 'star', 'galaxy' or 'comet'!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
