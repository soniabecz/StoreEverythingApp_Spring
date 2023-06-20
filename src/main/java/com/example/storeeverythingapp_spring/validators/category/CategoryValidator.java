package com.example.storeeverythingapp_spring.validators.category;

import com.example.storeeverythingapp_spring.data.Category;
import com.example.storeeverythingapp_spring.data.db.CategoryEntity;
import com.example.storeeverythingapp_spring.repositories.InformationRepository;
import com.example.storeeverythingapp_spring.services.AppService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidator implements ConstraintValidator<CategoryValidation, String> {

    @Autowired
    AppService appService;

    public void initialize(CategoryValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(String category, ConstraintValidatorContext constraintValidatorContext) {

        ArrayList<CategoryEntity> categories = (ArrayList<CategoryEntity>) appService.getCategories();

        if (categories.size()==0){
            System.out.println("nope");
            return true;
        } else {
            for (CategoryEntity c : categories) {
                System.out.println(c);
                if (c.getName().equals(category)) {
                    return false;
                }
            }
        }

        return true;
    }
}
