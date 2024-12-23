package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.CpuCoolerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CpuCoolerRepository extends JpaRepository<CpuCoolerEntity, Integer> {
}
