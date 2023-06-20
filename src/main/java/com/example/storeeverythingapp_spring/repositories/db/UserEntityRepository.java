package com.example.storeeverythingapp_spring.repositories.db;

import com.example.storeeverythingapp_spring.data.db.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUsername(String username);
}