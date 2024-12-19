package com.example.demo.service;

import com.example.demo.domain.entity.CommunityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.repository.CommunityRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommunityService {

    private final CommunityRepository communityRepository;

    public List<CommunityEntity> CommunityList() {
        return communityRepository.findAll();
    }

}
