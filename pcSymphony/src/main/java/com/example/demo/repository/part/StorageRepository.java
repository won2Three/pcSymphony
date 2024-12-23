package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<StorageEntity, Integer> {
}
