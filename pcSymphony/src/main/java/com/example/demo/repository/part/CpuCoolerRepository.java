package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.CpuCoolerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CpuCoolerRepository extends JpaRepository<CpuCoolerEntity, Integer> {
    Page<CpuCoolerEntity> findByNameContainingOrManufacturerContaining(String name, String manufacturer, Pageable pageable);

    @Query("SELECT c FROM CpuCoolerEntity c")
    List<CpuCoolerEntity> findAllCpuCoolers(); // ✅ 모든 CPU 쿨러 조회
}