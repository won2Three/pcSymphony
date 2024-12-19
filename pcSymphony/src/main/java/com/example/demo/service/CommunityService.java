package com.example.demo.service;

import com.example.demo.domain.dto.CommunityDto;
import com.example.demo.domain.entity.CommunityEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.repository.CommunityRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CommunityService {

    private final CommunityRepository communityRepository;

    public List<CommunityDto> getList() {
        //entity 기반 모든 게시글 조회
        List<CommunityEntity> entities = communityRepository.findAll();

        //반환할 DTO 리스트
        List<CommunityDto> dtoList = new ArrayList<>();

        for (CommunityEntity entity : entities) {
            CommunityDto dto = CommunityDto.builder()
                    .communityId(entity.getCommunityId())
                    .communityContent(entity.getCommunityContent())
                    .communityDate(String.valueOf(entity.getCommunityDate()))
                    .communityTitle(entity.getCommunityTitle())
                    .communityView(String.valueOf(entity.getCommunityView()))
                    .memberId(entity.getMember().getMemberId())
                    .build();

            dtoList.add(dto);
        }
        return dtoList;
    }

    //글쓰기
    //read
    //삭제
}

