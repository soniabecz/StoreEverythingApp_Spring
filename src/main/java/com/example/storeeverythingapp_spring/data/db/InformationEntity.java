package com.example.storeeverythingapp_spring.data.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "INFORMATION", schema = "PUBLIC", catalog = "STOREEVERYTHING")
public class InformationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private int id;
    @Basic
    @Column(name = "TITLE", nullable = false, length = 255)
    private String title;
    @Basic
    @Column(name = "CONTENT", nullable = false, length = 255)
    private String content;
    @Basic
    @Column(name = "DATE", nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID", nullable = false)
    private CategoryEntity categoryName;
    @Basic
    @Column(name = "LINK", nullable = true, length = 255)
    private String link;
    @Basic
    @Column(name = "USERNAME", nullable = false, length = 20)
    private String username;
    @Basic
    @Column(name = "SHARED_TO", nullable = true, length = 20)
    private String sharedTo;
}
