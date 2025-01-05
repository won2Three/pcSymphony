package com.example.demo.repository;

import com.example.demo.domain.entity.PartsReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartsReviewRepository extends JpaRepository<PartsReviewEntity, Integer> {
    // 필요한 경우 사용자 정의 쿼리 메서드 추가
}
