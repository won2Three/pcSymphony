package com.example.demo.repository;

import com.example.demo.domain.entity.CommunityReplyEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityReplyRepository extends JpaRepository<CommunityReplyEntity, Integer> {
    List<CommunityReplyEntity> findByCommunity_communityId(int communityId, Sort sort);
}
