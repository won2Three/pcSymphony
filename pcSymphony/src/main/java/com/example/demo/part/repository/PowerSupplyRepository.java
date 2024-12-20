package com.example.demo.part.repository;

import com.example.demo.part.entity.PowerSupplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerSupplyRepository extends JpaRepository<PowerSupplyEntity, Integer> {
}
