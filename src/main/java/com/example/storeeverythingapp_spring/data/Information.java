package com.example.storeeverythingapp_spring.data;

import com.example.storeeverythingapp_spring.data.Category;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Information {
    private String title;
    private String content;
    private String date;
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
