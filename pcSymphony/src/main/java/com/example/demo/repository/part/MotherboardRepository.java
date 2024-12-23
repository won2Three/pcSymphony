package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.MotherboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotherboardRepository extends JpaRepository<MotherboardEntity, Integer> {
}
