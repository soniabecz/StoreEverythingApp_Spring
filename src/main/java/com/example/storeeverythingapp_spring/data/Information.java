package com.example.storeeverythingapp_spring.data;

import com.example.storeeverythingapp_spring.data.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Information {

    @Size(min = 3, max = 20, message = "Name has to consist of more than 3 and less than 20 characters")
    private String title;

    @Size(min = 5, max = 500, message = "Name has to consist of more than 5 and less than 500 characters")
    private String content;

    //@FutureOrPresent(message = "You can't choose past date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date can't be null")
    private Date date;

    @Valid
    private Category category;

    private String link;

    //private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
}
