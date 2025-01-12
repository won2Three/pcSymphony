package com.example.demo.repository;

import com.example.demo.domain.entity.PcReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PcReviewRepository extends JpaRepository<PcReviewEntity, Integer> {

    // JpaRepository를 상속하면 기본적인 CRUD 메서드를 사용할 수 있음
    // 필요에 따라 사용자 정의 메서드를 추가할 수 있음
}
