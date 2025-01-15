package com.example.demo.service;

import com.example.demo.domain.dto.CommunityDTO;
import com.example.demo.domain.dto.CommunityReplyDTO;
import com.example.demo.domain.entity.CommunityEntity;
import com.example.demo.domain.entity.CommunityReplyEntity;
import com.example.demo.domain.entity.MemberEntity;
import com.example.demo.repository.CommunityReplyRepository;
import com.example.demo.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.demo.repository.CommunityRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommunityService {

    private final CommunityRepository communityRepository;

    private final MemberRepository memberRepository;

    private final CommunityReplyRepository replyRepository;

    // 커뮤니티 목록 (페이지네이션 적용)
    public Page<CommunityDTO> getList(int page, int size) {
        // 페이지 처리 위한 Pageable 객체 생성
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("communityId")));

        // 커뮤니티 목록을 Page 객체로 반환
        Page<CommunityEntity> communityPage = communityRepository.findAll(pageRequest);

        // Page<CommunityDTO> 형태로 변환
        return communityPage.map(entity -> CommunityDTO.builder()
                .communityId(entity.getCommunityId())
                .communityContent(entity.getCommunityContent())
                .communityDate(entity.getCommunityDate())
                .communityTitle(entity.getCommunityTitle())
                .communityView(entity.getCommunityView())
                .memberId(entity.getMember().getMemberId())
                .build());
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
                .communityView(0) //조회수 초기화
                .imagePath(communityDto.getImagePath())
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
                .communityView(communityEntity.getCommunityView())
                .communityDate(communityEntity.getCommunityDate())
                .memberId(communityEntity.getMember().getMemberId())
                .imagePath(communityEntity.getImagePath())
                .build();

        return communityDto;
    }

    // 수정 코드
    public void update(int communityId, CommunityDTO communityDto, String username, MultipartFile imageUpload) {
        // 사용자 확인
        MemberEntity memberEntity = memberRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자 정보가 없습니다."));

        // 게시글 확인
        CommunityEntity communityEntity = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("글이 없습니다."));

        // 작성자 확인
        if (!communityEntity.getMember().getMemberId().equals(username)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        // 이미지가 새로 업로드된 경우 처리
        if (imageUpload != null && !imageUpload.isEmpty()) {
            // 새로운 이미지 저장
            String imagePath = saveImageToFileSystem(imageUpload); // 이미지 저장 메서드
            communityDto.setImagePath(imagePath);  // 새 이미지 경로 설정
            log.info("새 이미지 경로: " + imagePath);  // 로그로 확인
        } else {
            // 새 이미지를 업로드하지 않은 경우 기존 이미지 경로 유지
            communityDto.setImagePath(communityEntity.getImagePath());
            log.info("기존 이미지 경로 사용: " + communityEntity.getImagePath());
        }

        // 게시글 수정
        communityEntity.setCommunityTitle(communityDto.getCommunityTitle());
        communityEntity.setCommunityContent(communityDto.getCommunityContent());
        communityEntity.setImagePath(communityDto.getImagePath());  // 이미지 경로 업데이트
        communityRepository.save(communityEntity);  // DB에 저장
    }




    //삭제
    public void delete(int communityId, String username) {
        MemberEntity memberEntity = memberRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자 정보가 없습니다."));

        CommunityEntity communityEntity = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("글이 없습니다."));

        if (!username.equals(memberEntity.getMemberId())) {
            throw new RuntimeException("삭제 권한이 없습니다(서비스).");
        }

        communityRepository.delete(communityEntity);
    }

    //댓글 저장
    public void communityReplyWrite(CommunityReplyDTO replyDTO) {

        MemberEntity memberEntity = memberRepository.findById(replyDTO.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("사용자 정보가 없습니다."));
        CommunityEntity communityEntity = communityRepository.findById(replyDTO.getCommunityId())
                .orElseThrow(() -> new EntityNotFoundException("게시글 정보가 없습니다."));
        CommunityReplyEntity communityReplyEntity = CommunityReplyEntity.builder()
                .member(memberEntity)
                .community(communityEntity)
                .communityReplyContent(replyDTO.getReplyContent())
                .build();

        replyRepository.save(communityReplyEntity);
    }

    //리플 목록
    public List<CommunityReplyDTO> getReplyList(int communityId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "community.communityId");
        List<CommunityReplyEntity> replyEntityList = replyRepository.findByCommunity_communityId(communityId, sort);
        List<CommunityReplyDTO> replyDTOList = new ArrayList<CommunityReplyDTO>();

        for (CommunityReplyEntity replyEntity : replyEntityList) {
            CommunityReplyDTO replyDTO = CommunityReplyDTO.builder()
                    .communityReplyId(replyEntity.getCommunityReplyId())
                    .communityId(replyEntity.getCommunity().getCommunityId())
                    .memberId(replyEntity.getMember().getMemberId())
                    .replyContent(replyEntity.getCommunityReplyContent())
                    .communityReplyDate(replyEntity.getCommunityReplyDate())
                    .build();
            replyDTOList.add(replyDTO);
        }
        return replyDTOList;
    }

    //댓글 삭제
    public void communityReplyDelete(Integer communityReplyId, String username) {
        // 댓글 ID를 기준으로 댓글 엔티티를 조회
        CommunityReplyEntity replyEntity = replyRepository.findById(communityReplyId)
                .orElseThrow(() -> new EntityNotFoundException("댓글 정보가 없습니다"));

        // 댓글 작성자와 현재 사용자가 일치하는지 확인
        if (!username.equals(replyEntity.getMember().getMemberId())) {
            throw new RuntimeException("삭제 권한이 없습니다");
        }

        // 댓글 삭제
        replyRepository.delete(replyEntity);
    }

    //조회수 증가
    @Transactional
    public void incrementViewCount(Integer communityId) {
        CommunityEntity community = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));
        community.setCommunityView(community.getCommunityView() + 1);
        communityRepository.save(community);
    }

    // 이미지 파일을 서버의 외부 경로에 저장하는 메서드
    public String saveImageToFileSystem(MultipartFile imageUpload) {
        try {
            // 외부 경로 설정 (예시: C:/uploads/)
            String uploadDir = "C:/uploads/";

            // 업로드 디렉토리가 없으면 생성
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 파일 이름 설정 (현재 시간을 이용한 고유 이름 생성)
            String fileName = System.currentTimeMillis() + "-" + imageUpload.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);

            // 파일을 지정된 경로에 저장
            imageUpload.transferTo(filePath.toFile());

            // 웹에서 접근할 수 있도록 경로 반환
            return "/uploads/" + fileName;  // 저장된 파일 경로 반환
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
