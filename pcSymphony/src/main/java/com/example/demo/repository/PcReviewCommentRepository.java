package com.example.demo.repository;

import com.example.demo.domain.entity.PcReviewCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PcReviewCommentRepository extends JpaRepository<PcReviewCommentEntity, Integer> {
    List<PcReviewCommentEntity> findByPcReview_PcreviewId(int pcreviewId);

}
