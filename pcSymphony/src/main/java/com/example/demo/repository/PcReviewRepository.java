package com.example.demo.repository;

import com.example.demo.domain.entity.PcReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PcReviewRepository extends JpaRepository<PcReviewEntity, Integer> {
    Page<PcReviewEntity> findByPcreviewTitleContainingIgnoreCase(String keyword, Pageable pageable);
    List<PcReviewEntity> findTop10ByOrderByPcreviewIdDesc();
}
