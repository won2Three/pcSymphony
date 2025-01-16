package com.example.demo.repository;

import com.example.demo.domain.entity.GuidelineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuidelineRepository extends JpaRepository<GuidelineEntity, Integer> {

}
