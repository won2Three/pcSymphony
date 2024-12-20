package com.example.demo.part.repository;

import com.example.demo.part.entity.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<StorageEntity, Integer> {
}
