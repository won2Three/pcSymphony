package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.VideoCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoCardRepository extends JpaRepository<VideoCardEntity, Integer> {
}
