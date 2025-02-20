package com.example.demo.service;

import com.example.demo.domain.dto.PcReviewCommentDTO;
import com.example.demo.domain.dto.PcReviewDTO;
import com.example.demo.domain.entity.MemberEntity;
import com.example.demo.domain.entity.PcReviewCommentEntity;
import com.example.demo.domain.entity.PcReviewEntity;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PcReviewCommentRepository;
import com.example.demo.repository.PcReviewRepository;
import com.example.demo.repository.part.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PcReviewService {

    private final PcReviewRepository pcReviewRepository;
    private final PartsReviewRepository partsReviewRepository;
    private final MemberRepository memberRepository;
    @Autowired
    private final PcReviewCommentRepository pcReviewCommentRepository;
    /**
     * Save PcReviewEntity from PcReviewDTO
     *
     * @param pcReviewDTO DTO containing PC review data
     * @return Saved PcReviewEntity
     */
    public PcReviewEntity savePcReview(PcReviewDTO pcReviewDTO) {
        // DTO를 Entity로 변환
        PcReviewEntity pcReviewEntity = toEntity(pcReviewDTO);

        // 작성 날짜 설정
        pcReviewEntity.setPcreviewDate(LocalDateTime.now());

        // 저장
        return pcReviewRepository.save(pcReviewEntity);
    }

    /**
     * Retrieve PcReviewDTO by ID
     *
     * @param id PC review ID
     * @return PcReviewDTO if found
     */
    public Optional<PcReviewDTO> getPcReviewById(int id) {
        return pcReviewRepository.findById(id).map(this::toDto);
    }

    /**
     * Convert PcReviewDTO to PcReviewEntity
     *
     * @param pcReviewDTO DTO containing PC review data
     * @return Converted PcReviewEntity
     */
    public PcReviewEntity toEntity(PcReviewDTO pcReviewDTO) {
        return PcReviewEntity.builder()
                .pcreviewId(pcReviewDTO.getPcreviewId())
                .pcreviewTitle(pcReviewDTO.getPcreviewTitle())
                .pcreviewContent(pcReviewDTO.getPcreviewContent())
                .pcreviewDate(pcReviewDTO.getPcreviewDate())
                .imagePath(pcReviewDTO.getImagePath())
                .userId(pcReviewDTO.getUserId())
                .cpuReview(
                        partsReviewRepository.findById(pcReviewDTO.getCpuReviewId())
                                .orElseThrow(() -> new EntityNotFoundException("CPU Review not found with id: " + pcReviewDTO.getCpuReviewId()))
                )
                .cpucoolerReview(
                        partsReviewRepository.findById(pcReviewDTO.getCpucoolerReviewId())
                                .orElseThrow(() -> new EntityNotFoundException("CPU Cooler Review not found with id: " + pcReviewDTO.getCpucoolerReviewId()))
                )
                .motherboardReview(
                        partsReviewRepository.findById(pcReviewDTO.getMotherboardReviewId())
                                .orElseThrow(() -> new EntityNotFoundException("Motherboard Review not found with id: " + pcReviewDTO.getMotherboardReviewId()))
                )
                .memoryReview(
                        partsReviewRepository.findById(pcReviewDTO.getMemoryReviewId())
                                .orElseThrow(() -> new EntityNotFoundException("Memory Review not found with id: " + pcReviewDTO.getMemoryReviewId()))
                )
                .storageReview(
                        partsReviewRepository.findById(pcReviewDTO.getStorageReviewId())
                                .orElseThrow(() -> new EntityNotFoundException("Storage Review not found with id: " + pcReviewDTO.getStorageReviewId()))
                )
                .videocardReview(
                        partsReviewRepository.findById(pcReviewDTO.getVideocardReviewId())
                                .orElseThrow(() -> new EntityNotFoundException("Video Card Review not found with id: " + pcReviewDTO.getVideocardReviewId()))
                )
                .powersupplyReview(
                        partsReviewRepository.findById(pcReviewDTO.getPowersupplyReviewId())
                                .orElseThrow(() -> new EntityNotFoundException("Power Supply Review not found with id: " + pcReviewDTO.getPowersupplyReviewId()))
                )
                .coverReview(
                        partsReviewRepository.findById(pcReviewDTO.getCoverReviewId())
                                .orElseThrow(() -> new EntityNotFoundException("Cover Review not found with id: " + pcReviewDTO.getCoverReviewId()))
                )
                .build();
    }

    /**
     * Convert PcReviewEntity to PcReviewDTO
     *
     * @param pcReviewEntity Entity containing PC review data
     * @return Converted PcReviewDTO
     */
    public PcReviewDTO toDto(PcReviewEntity pcReviewEntity) {

        return PcReviewDTO.builder()
                .pcreviewId(pcReviewEntity.getPcreviewId())
                .pcreviewTitle(pcReviewEntity.getPcreviewTitle())
                .pcreviewContent(pcReviewEntity.getPcreviewContent())
                .pcreviewDate(pcReviewEntity.getPcreviewDate())
                .userId(pcReviewEntity.getUserId())
                .imagePath(pcReviewEntity.getImagePath())
                .cpuReviewId(pcReviewEntity.getCpuReview().getPartsReviewId())
                .cpucoolerReviewId(pcReviewEntity.getCpucoolerReview().getPartsReviewId())
                .motherboardReviewId(pcReviewEntity.getMotherboardReview().getPartsReviewId())
                .memoryReviewId(pcReviewEntity.getMemoryReview().getPartsReviewId())
                .storageReviewId(pcReviewEntity.getStorageReview().getPartsReviewId())
                .videocardReviewId(pcReviewEntity.getVideocardReview().getPartsReviewId())
                .powersupplyReviewId(pcReviewEntity.getPowersupplyReview().getPartsReviewId())
                .coverReviewId(pcReviewEntity.getCoverReview().getPartsReviewId())
                .build();
    }

    public void updatePcReview(int reviewId, String reviewTitle, String reviewContent) {
        PcReviewEntity pcReview = pcReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다. ID: " + reviewId));

        pcReview.setPcreviewTitle(reviewTitle);
        pcReview.setPcreviewContent(reviewContent);

        pcReviewRepository.save(pcReview); // 변경 사항 저장
    }

    public void updatePcReview(int reviewId, String reviewTitle, String reviewContent, String imagePath) {
        PcReviewEntity pcReview = pcReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다. ID: " + reviewId));

        pcReview.setPcreviewTitle(reviewTitle);
        pcReview.setPcreviewContent(reviewContent);
        pcReview.setImagePath(imagePath);


        pcReviewRepository.save(pcReview); // 변경 사항 저장
    }



    public void pcReviewCommentWrite(PcReviewCommentDTO commentDTO) {
        // 리뷰를 찾아서 설정
        PcReviewEntity pcReview = pcReviewRepository.findById(commentDTO.getPcreviewId())
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다. ID: " + commentDTO.getPcreviewId()));

        // 사용자 정보를 찾아서 설정
        MemberEntity user = memberRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. ID: " + commentDTO.getUserId()));

        // 댓글 엔티티 생성
        PcReviewCommentEntity commentEntity = PcReviewCommentEntity.builder()
                .pcreviewCommentContent(commentDTO.getPcreviewCommentContent())
                .pcreviewCommentDate(LocalDateTime.now())
                .pcReview(pcReview)
                .user(user)
                .build();

        // 댓글 저장
        pcReviewCommentRepository.save(commentEntity);
    }
    public List<PcReviewCommentDTO> getCommentsByPcReviewId(int pcreviewId) {
        return pcReviewCommentRepository.findByPcReview_PcreviewId(pcreviewId).stream()
                .map(comment -> PcReviewCommentDTO.builder()
                        .pcreviewCommentId(comment.getPcreviewCommentId())
                        .pcreviewCommentContent(comment.getPcreviewCommentContent())
                        .pcreviewCommentDate(comment.getPcreviewCommentDate())
                        .pcreviewId(comment.getPcReview().getPcreviewId())
                        .userId(comment.getUser() != null ? comment.getUser().getMemberId() : null)
                        .build())
                .collect(Collectors.toList());
    }

    public void deleteComment(int commentId) {
        PcReviewCommentEntity comment = pcReviewCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다. ID: " + commentId));
        pcReviewCommentRepository.delete(comment);
    }

    public void deletePcReview(int reviewId) {
        PcReviewEntity review = pcReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. ID: " + reviewId));
        pcReviewRepository.delete(review);
    }
    //pcReviewList 게시글 페이징
    public Page<PcReviewEntity> pcReviewList(Pageable pageable) {
        return pcReviewRepository.findAll(pageable);
    }

    public Page<PcReviewDTO> getAllPcReviews(Pageable pageable) {
        return pcReviewRepository.findAll(pageable).map(this::toDto);
    }

    public Page<PcReviewDTO> searchPcReviews(String keyword, Pageable pageable) {
        return pcReviewRepository
                .findByPcreviewTitleContainingIgnoreCase(keyword, pageable)
                .map(this::toDto);
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

    public List<PcReviewEntity> getLatestPcReviews() {
        return pcReviewRepository.findTop10ByOrderByPcreviewIdDesc();
    }
}