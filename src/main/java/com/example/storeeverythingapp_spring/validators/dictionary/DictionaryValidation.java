package com.example.storeeverythingapp_spring.validators.dictionary;

import com.example.storeeverythingapp_spring.validators.category.CategoryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DictionaryValidator.class)
public @interface DictionaryValidation {
    public String message() default "Word doesn't exist in a dictionary";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
