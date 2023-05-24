package com.example.storeeverythingapp_spring.validators.category;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<CategoryValidation, String> {

    public void initialize(CategoryValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(String category, ConstraintValidatorContext constraintValidatorContext) {

        char[] charArray = category.toCharArray();

        for (char c : charArray) {

            if (!Character.isLowerCase(c))
                return false;
        }

        return true;
    }
}
