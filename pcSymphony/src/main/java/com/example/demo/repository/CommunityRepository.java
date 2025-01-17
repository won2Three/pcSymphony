package com.example.demo.repository;

import com.example.demo.domain.entity.CommunityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Integer> {
    Page<CommunityEntity> findByCommunityTitleContainingIgnoreCase(String title, Pageable pageable);
}
