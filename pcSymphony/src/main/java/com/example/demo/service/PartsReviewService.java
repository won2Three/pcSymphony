package com.example.demo.service;

import com.example.demo.domain.dto.PartDTO;
import com.example.demo.domain.dto.PartsReviewDTO;
import com.example.demo.domain.entity.part.PartsReviewEntity;
import com.example.demo.domain.entity.part.*;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.part.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * 게시판 서비스
 */
@RequiredArgsConstructor
@Service
@Transactional
public class PartsReviewService {


    public static PartsReviewDTO toDTO(PartsReviewEntity entity) {
        PartsReviewDTO dto = new PartsReviewDTO();
        dto.setPartsReviewId(entity.getPartsReviewId());
        dto.setPartsReviewRating(entity.getPartsReviewRating());
        dto.setPartsReviewTitle(entity.getPartsReviewTitle());
        dto.setPartsReviewContent(entity.getPartsReviewContent());
        dto.setMemberId(entity.getMember().getMemberId());
        dto.setCpuId(entity.getCpu().getId());
        dto.setCpucoolerId(entity.getCpucooler().getId());
        dto.setMotherboardId(entity.getMotherboard().getId());
        dto.setMemoryId(entity.getMemory().getId());
        dto.setStorageId(entity.getStorage().getId());
        dto.setVideocardId(entity.getVideocard().getId());
        dto.setPowersupplyId(entity.getPowersupply().getId());
        dto.setCoverId(entity.getCover().getId());
        return dto;
    }

    public static PartsReviewEntity toEntity(PartsReviewDTO dto,
                                             MemberRepository memberRepository,
                                             CpuRepository cpuRepository,
                                             CpuCoolerRepository cpuCoolerRepository,
                                             MotherboardRepository motherboardRepository,
                                             MemoryRepository memoryRepository,
                                             StorageRepository storageRepository,
                                             VideoCardRepository videoCardRepository,
                                             PowerSupplyRepository powerSupplyRepository,
                                             CoverRepository coverRepository) {
        PartsReviewEntity entity = new PartsReviewEntity();
        entity.setPartsReviewId(dto.getPartsReviewId());
        entity.setPartsReviewRating(dto.getPartsReviewRating());
        entity.setPartsReviewTitle(dto.getPartsReviewTitle());
        entity.setPartsReviewContent(dto.getPartsReviewContent());
//        entity.setMember(dto.getMemberId());
        entity.setMember(memberRepository.findById(dto.getMemberId()).orElse(null));
        entity.setCpu(cpuRepository.findById(dto.getCpuId()).orElse(null));
        entity.setCpucooler(cpuCoolerRepository.findById(dto.getCpucoolerId()).orElse(null));
        entity.setMotherboard(motherboardRepository.findById(dto.getMotherboardId()).orElse(null));
        entity.setMemory(memoryRepository.findById(dto.getMemoryId()).orElse(null));
        entity.setStorage(storageRepository.findById(dto.getStorageId()).orElse(null));
        entity.setVideocard(videoCardRepository.findById(dto.getVideocardId()).orElse(null));
        entity.setPowersupply(powerSupplyRepository.findById(dto.getPowersupplyId()).orElse(null));
        entity.setCover(coverRepository.findById(dto.getCoverId()).orElse(null));

        // Member 설정
        if (dto.getMemberId() != null) {
            entity.setMember(memberRepository.findById((dto.getMemberId())).orElse(null));
        }

        // CPU 설정
        if (dto.getCpuId() != null) {
            entity.setCpu(cpuRepository.findById(dto.getCpuId()).orElse(null));
        }

        // CPU Cooler 설정
        if (dto.getCpucoolerId() != null) {
            entity.setCpucooler(cpuCoolerRepository.findById(dto.getCpucoolerId()).orElse(null));
        }

        // 기타 필드 설정...
        if (dto.getMotherboardId() != null) {
            entity.setMotherboard(motherboardRepository.findById(dto.getMotherboardId()).orElse(null));
        }
        if (dto.getMemoryId() != null) {
            entity.setMemory(memoryRepository.findById(dto.getMemoryId()).orElse(null));
        }
        if (dto.getStorageId() != null) {
            entity.setStorage(storageRepository.findById(dto.getStorageId()).orElse(null));
        }
        if (dto.getVideocardId() != null) {
            entity.setVideocard(videoCardRepository.findById(dto.getVideocardId()).orElse(null));
        }
        if (dto.getPowersupplyId() != null) {
            entity.setPowersupply(powerSupplyRepository.findById(dto.getPowersupplyId()).orElse(null));
        }
        if (dto.getCoverId() != null) {
            entity.setCover(coverRepository.findById(dto.getCoverId()).orElse(null));
        }
        return entity;
    }
    @Autowired
    private final PartsReviewRepository partsReviewRepository;

    public PartsReviewEntity updatePartsReview(Integer id, String title, String content, int rating) {
        // 엔티티 조회
        PartsReviewEntity review = partsReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다: ID " + id));

        // 엔티티 수정
        review.setPartsReviewTitle(title);
        review.setPartsReviewContent(content);
        review.setPartsReviewRating(rating);
//        review.setPartsReviewDate();

        // 저장 및 반환
        return partsReviewRepository.save(review);
    }
}