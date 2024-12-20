package com.example.demo.part.repository;

import com.example.demo.part.entity.CoverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverRepository extends JpaRepository<CoverEntity, Integer> {
}
