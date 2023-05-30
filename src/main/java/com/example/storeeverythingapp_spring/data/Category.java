package com.example.storeeverythingapp_spring.data;

import com.example.storeeverythingapp_spring.validators.category.CategoryValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Size(min = 3, max = 20, message = "Name has to consist of more than 3 and less than 20 characters")
    @Pattern(regexp = "[a-z]+", message = "Name should only consist of lowercase letters")
    @NotBlank(message = "Category has to be chosen")
    @CategoryValidation
    private String name;
}
