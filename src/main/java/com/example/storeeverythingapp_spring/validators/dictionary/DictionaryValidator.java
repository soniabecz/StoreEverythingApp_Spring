package com.example.storeeverythingapp_spring.validators.dictionary;

import com.example.storeeverythingapp_spring.services.RestClientService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class DictionaryValidator implements ConstraintValidator<DictionaryValidation, String> {

    @Autowired
    RestClientService clientService;

    @Override
    public void initialize(DictionaryValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return clientService.checkIfWordIsInDictionary(s);
    }
}
