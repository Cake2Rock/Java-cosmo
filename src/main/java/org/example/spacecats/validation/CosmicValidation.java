package org.example.spacecats.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CosmicValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CosmicValidation {
    String message() default "Field must contain cosmic words like 'star', 'galaxy', or 'comet'.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
