package com.example.demo.repository;

import com.example.demo.domain.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Integer> {
    //public List<CommunityEntity> selectAll();

    @Modifying
    @Transactional
    @Query("update CommunityEntity c set c.communityView = c.communityView + 1 where c.communityId = :communityId")
    void viewCount(Integer communityId);
}
