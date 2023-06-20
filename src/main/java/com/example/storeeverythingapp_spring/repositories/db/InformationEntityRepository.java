package com.example.storeeverythingapp_spring.repositories.db;

import com.example.storeeverythingapp_spring.data.db.InformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InformationEntityRepository extends JpaRepository<InformationEntity, Integer> {

    List<InformationEntity> findAllByUsername(String username);

    List<InformationEntity> findAllBySharedTo(String username);
}