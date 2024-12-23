package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.PowerSupplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerSupplyRepository extends JpaRepository<PowerSupplyEntity, Integer> {
}
