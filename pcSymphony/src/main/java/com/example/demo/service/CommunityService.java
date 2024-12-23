package com.example.demo.service;

import com.example.demo.domain.dto.CommunityDTO;
import com.example.demo.domain.entity.CommunityEntity;
import com.example.demo.domain.entity.MemberEntity;
import com.example.demo.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
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

    private final MemberRepository memberRepository;

    //커뮤니티 메인
    public List<CommunityDTO> getList() {
        //entity 기반 모든 게시글 조회
        List<CommunityEntity> entities = communityRepository.findAll();

        //반환할 DTO 리스트
        List<CommunityDTO> dtoList = new ArrayList<>();

        for (CommunityEntity entity : entities) {
            CommunityDTO dto = CommunityDTO.builder()
                    .communityId(entity.getCommunityId())
                    .communityContent(entity.getCommunityContent())
                    .communityDate(entity.getCommunityDate())
                    .communityTitle(entity.getCommunityTitle())
                    .communityView(String.valueOf(entity.getCommunityView()))
                    .memberId(entity.getMember().getMemberId())
                    .build();

            dtoList.add(dto);
        }
        return dtoList;
    }

    //글쓰기
    public void write(CommunityDTO communityDto) {
        MemberEntity memberEntity = memberRepository.findById(communityDto.getMemberId())
                .orElseThrow(()
                        -> new EntityNotFoundException("회원정보가 없습니다"));
        CommunityEntity communityEntity = CommunityEntity.builder()
                .member(memberEntity)
                .communityTitle(communityDto.getCommunityTitle())
                .communityContent(communityDto.getCommunityContent())
                .build();

        communityRepository.save(communityEntity);
    }

    //read
    public CommunityDTO getCommunity(int communityId) {
        CommunityEntity communityEntity = communityRepository.findById(communityId)
                .orElseThrow(() ->
                        new EntityNotFoundException("글이 없습니다."));
        CommunityDTO communityDto = CommunityDTO.builder()
                .communityId(communityEntity.getCommunityId())
                .communityTitle(communityEntity.getCommunityTitle())
                .communityContent(communityEntity.getCommunityContent())
                .communityView(String.valueOf(communityEntity.getCommunityView()))
                .communityDate(communityEntity.getCommunityDate())
                .memberId(communityEntity.getMember().getMemberId())
                .build();

        return communityDto;
    }

    //삭제
    public void delete(int boardNum, String username) {
        MemberEntity memberEntity = memberRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자 정보가 없습니다."));

        CommunityEntity communityEntity = communityRepository.findById(boardNum)
                .orElseThrow(() -> new EntityNotFoundException("글이 없습니다."));

        if (!username.equals(memberEntity.getMemberId())) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        communityRepository.delete(communityEntity);
    }

    //수정
    public void update(int communityId, CommunityDTO communityDto) {
//    public void update(int communityId, CommunityDTO communityDto, String username) {
//         //사용자 확인
//        MemberEntity memberEntity = memberRepository.findById(username)
//                .orElseThrow(() -> new EntityNotFoundException("사용자 정보가 없습니다."));

        // 게시글 확인
        CommunityEntity communityEntity = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("글이 없습니다."));

//        // 작성자 확인
//        if (!communityEntity.getMember().getMemberId().equals(username)) {
//            throw new RuntimeException("수정 권한이 없습니다.");
//        }

        // 게시글 수정
        communityEntity.setCommunityTitle(communityDto.getCommunityTitle());
        communityEntity.setCommunityContent(communityDto.getCommunityContent());

        communityRepository.save(communityEntity);
    }
}
