package com.example.storeeverythingapp_spring.data.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "AUTHORITIES", schema = "PUBLIC", catalog = "STOREEVERYTHING")
public class AuthoritiesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private int id;
    @Basic
    @Column(name = "USERNAME", nullable = false, length = 20)
    private String username;
    @Basic
    @Column(name = "AUTHORITY", nullable = false, length = 50)
    private String authority;
}
