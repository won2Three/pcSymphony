package com.example.demo.part.repository;

import com.example.demo.part.entity.CpuCoolerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CpuCoolerRepository extends JpaRepository<CpuCoolerEntity, Integer> {
}
