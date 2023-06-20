package com.example.storeeverythingapp_spring.data.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CATEGORY", schema = "PUBLIC", catalog = "STOREEVERYTHING")
@Data
@NoArgsConstructor
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private int id;
    @Basic
    @Column(name = "NAME", nullable = false, length = 20)
    private String name;

}
