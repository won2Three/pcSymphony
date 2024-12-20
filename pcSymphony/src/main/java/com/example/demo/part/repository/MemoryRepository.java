package com.example.demo.part.repository;

import com.example.demo.part.entity.MemoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryRepository extends JpaRepository<MemoryEntity, Integer> {
}
