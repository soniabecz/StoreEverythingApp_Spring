package com.example.storeeverythingapp_spring.validators.category;

import com.example.storeeverythingapp_spring.data.Category;
import com.example.storeeverythingapp_spring.repositories.InformationRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidator implements ConstraintValidator<CategoryValidation, String> {

    @Autowired
    InformationRepository informationRepository = new InformationRepository();

    public void initialize(CategoryValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(String category, ConstraintValidatorContext constraintValidatorContext) {

        ArrayList<Category> categories = (ArrayList<Category>) informationRepository.getCategories();

        if (categories.size()==0){
            System.out.println("nope");
            return true;
        } else {
            for (Category c : categories) {
                System.out.println(c);
                if (c.getName().equals(category)) {
                    return false;
                }
            }
        }

        return true;
    }
}
