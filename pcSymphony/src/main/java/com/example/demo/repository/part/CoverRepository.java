package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.CoverEntity;
import com.example.demo.domain.entity.part.CpuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CoverRepository extends JpaRepository<CoverEntity, Integer> {
    Page<CpuEntity> findByNameContainingOrManufacturerContaining(String name, String manufacturer, Pageable pageable);
}
