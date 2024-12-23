package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.MemoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryRepository extends JpaRepository<MemoryEntity, Integer> {
}
