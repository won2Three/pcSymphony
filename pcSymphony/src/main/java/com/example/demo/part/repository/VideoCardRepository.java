package com.example.demo.part.repository;

import com.example.demo.part.entity.VideoCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoCardRepository extends JpaRepository<VideoCardEntity, Integer> {
}
