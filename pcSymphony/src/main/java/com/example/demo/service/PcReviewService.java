package com.example.demo.service;

import com.example.demo.domain.dto.PcReviewDTO;
import com.example.demo.domain.entity.PcReviewEntity;
import com.example.demo.repository.PcReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class PcReviewService {

    private final PcReviewRepository pcReviewRepository;

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
                .cpuReviewId(pcReviewDTO.getCpuReviewId())
                .cpucoolerReviewId(pcReviewDTO.getCpucoolerReviewId())
                .motherboardId(pcReviewDTO.getMotherboardReviewId())
                .memoryReviewId(pcReviewDTO.getMemoryReviewId())
                .storageReviewId(pcReviewDTO.getStorageReviewId())
                .videocardReviewId(pcReviewDTO.getVideocardReviewId())
                .powersupplyReviewId(pcReviewDTO.getPowersupplyReviewId())
                .coverReviewId(pcReviewDTO.getCoverReviewId())
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
                .cpuReviewId(pcReviewEntity.getCpuReviewId())
                .cpucoolerReviewId(pcReviewEntity.getCpucoolerReviewId())
                .motherboardReviewId(pcReviewEntity.getMotherboardId())
                .memoryReviewId(pcReviewEntity.getMemoryReviewId())
                .storageReviewId(pcReviewEntity.getStorageReviewId())
                .videocardReviewId(pcReviewEntity.getVideocardReviewId())
                .powersupplyReviewId(pcReviewEntity.getPowersupplyReviewId())
                .coverReviewId(pcReviewEntity.getCoverReviewId())
                .build();
    }
}
