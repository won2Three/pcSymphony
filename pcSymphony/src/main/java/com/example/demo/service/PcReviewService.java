package com.example.demo.service;

import com.example.demo.domain.dto.PcReviewDTO;
import com.example.demo.domain.entity.PcReviewEntity;
import com.example.demo.repository.PcReviewRepository;
import com.example.demo.repository.part.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class PcReviewService {

    private final PcReviewRepository pcReviewRepository;
@Autowired
    private final PartsReviewRepository partsReviewRepository;
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
//


}