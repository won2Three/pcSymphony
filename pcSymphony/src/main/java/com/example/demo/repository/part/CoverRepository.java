package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.CoverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverRepository extends JpaRepository<CoverEntity, Integer> {
}
