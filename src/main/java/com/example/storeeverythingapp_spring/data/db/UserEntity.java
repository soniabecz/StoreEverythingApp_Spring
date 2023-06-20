package com.example.storeeverythingapp_spring.data.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "USERS", schema = "PUBLIC", catalog = "STOREEVERYTHING")
public class UserEntity {
    @Basic
    @Column(name = "NAME", nullable = false, length = 20)
    private String name;
    @Basic
    @Column(name = "SURNAME", nullable = false, length = 50)
    private String surname;

    @Id
    @Column(name = "USERNAME", nullable = false, length = 20)
    private String username;
    @Basic
    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;
    @Basic
    @Column(name = "AGE", nullable = false)
    private Integer age;
}
