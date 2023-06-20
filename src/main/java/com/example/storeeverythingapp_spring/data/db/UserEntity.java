package com.example.storeeverythingapp_spring.data.db;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Data
@NoArgsConstructor
@Table(name = "USERS", schema = "PUBLIC", catalog = "STOREEVERYTHING")
public class UserEntity {
    @Basic
    @Size(min = 3, max = 20, message = "Name has to consist of more than 3 and less than 20 characters")
    @Pattern(regexp = "[A-Z]\\w*", message = "Name should start with uppercase letter")
    @Column(name = "NAME", nullable = false, length = 20)
    private String name;
    @Basic
    @Size(min = 3, max = 50, message = "Surname has to consist of more than 3 and less than 50 characters")
    @Pattern(regexp = "[A-Z]\\w*", message = "Surname should start with uppercase letter")
    @Column(name = "SURNAME", nullable = false, length = 50)
    private String surname;

    @Id
    @Size(min = 3, max = 20, message = "Username has to consist of more than 3 and less than 20 characters")
    @Pattern(regexp = "[a-z]+", message = "Username should only consist of lowercase letters")
    @Column(name = "USERNAME", nullable = false, length = 20)
    private String username;
    @Basic
    @Size(min = 5, message = "Password has to consist of at least 5 characters")
    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;
    @Basic
    @Min(value = 18, message = "You have to be at least 18 years old")
    @Column(name = "AGE", nullable = false)
    private int age;
}
