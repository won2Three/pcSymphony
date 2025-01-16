package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.MemoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryRepository extends JpaRepository<MemoryEntity, Integer> {


    Page<MemoryEntity> findByNameContainingOrManufacturerContaining(String name, String manufacturer, Pageable pageable);
}
