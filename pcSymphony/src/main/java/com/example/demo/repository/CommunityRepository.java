package com.example.demo.repository;

import com.example.demo.domain.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Integer> {
    //public List<CommunityEntity> selectAll();
}
