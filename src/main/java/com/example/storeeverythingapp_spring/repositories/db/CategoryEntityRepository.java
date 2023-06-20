package com.example.storeeverythingapp_spring.repositories.db;

import com.example.storeeverythingapp_spring.data.db.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByNameIgnoreCase(String name);
}