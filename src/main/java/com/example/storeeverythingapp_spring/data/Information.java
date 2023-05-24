package com.example.storeeverythingapp_spring.data;

import com.example.storeeverythingapp_spring.data.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Information {

    @NotBlank
    @Size(min = 3, max = 20, message = "Name has to consist of more than 3 and less than 20 characters")
    private String title;

    @NotBlank
    @Size(min = 5, max = 500, message = "Name has to consist of more than 5 and less than 500 characters")
    private String content;

    private String date;

    @Valid
    private Category category;

    private String link;

    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public Information(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.date = formatter.format(new Date());
        this.category = category;
    }

    public Information(String title, String content, Category category, String link) {
        this.title = title;
        this.content = content;
        this.date = formatter.format(new Date());
        this.category = category;
        this.link = link;
    }

    public Information() {
        this.date = formatter.format(new Date());
    }
}
