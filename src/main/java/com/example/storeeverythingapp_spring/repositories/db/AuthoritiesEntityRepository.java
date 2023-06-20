package com.example.storeeverythingapp_spring.repositories.db;

import com.example.storeeverythingapp_spring.data.db.AuthoritiesEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthoritiesEntityRepository extends JpaRepository<AuthoritiesEntity, Integer> {

    List<AuthoritiesEntity> findByUsername(String username);
}