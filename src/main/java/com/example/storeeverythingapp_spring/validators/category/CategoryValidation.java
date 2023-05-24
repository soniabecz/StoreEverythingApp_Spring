package com.example.storeeverythingapp_spring.validators.category;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CategoryValidator.class)
public @interface CategoryValidation {
    public String message() default "Name should only consist of lowercase letters";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
