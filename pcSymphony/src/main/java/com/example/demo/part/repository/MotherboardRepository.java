package com.example.demo.part.repository;

import com.example.demo.part.entity.MotherboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotherboardRepository extends JpaRepository<MotherboardEntity, Integer> {
}
