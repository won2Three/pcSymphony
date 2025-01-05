package com.example.demo.service;

import com.example.demo.domain.dto.PartDTO;
import com.example.demo.domain.dto.PartsReviewDTO;
import com.example.demo.domain.entity.PartsReviewEntity;
import com.example.demo.domain.entity.part.*;
import com.example.demo.repository.part.*;
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
        dto.setPartsreviewId(entity.getPartsreviewId());
        dto.setPartsreviewRating(entity.getPartsreviewRating());
        dto.setPartsreviewTitle(entity.getPartsreviewTitle());
        dto.setPartsreviewContent(entity.getPartsreviewContent());
        dto.setUserId(entity.getUserId());
        dto.setCpuId(entity.getCpuId());
        dto.setCpucoolerId(entity.getCpucoolerId());
        dto.setMotherboardId(entity.getMotherboardId());
        dto.setMemoryId(entity.getMemoryId());
        dto.setStorageId(entity.getStorageId());
        dto.setVideocardId(entity.getVideocardId());
        dto.setPowersupplyId(entity.getPowersupplyId());
        dto.setCoverId(entity.getCoverId());
        return dto;
    }

    public static PartsReviewEntity toEntity(PartsReviewDTO dto) {
        PartsReviewEntity entity = new PartsReviewEntity();
        entity.setPartsreviewId(dto.getPartsreviewId());
        entity.setPartsreviewRating(dto.getPartsreviewRating());
        entity.setPartsreviewTitle(dto.getPartsreviewTitle());
        entity.setPartsreviewContent(dto.getPartsreviewContent());
        entity.setUserId(dto.getUserId());
        entity.setCpuId(dto.getCpuId());
        entity.setCpucoolerId(dto.getCpucoolerId());
        entity.setMotherboardId(dto.getMotherboardId());
        entity.setMemoryId(dto.getMemoryId());
        entity.setStorageId(dto.getStorageId());
        entity.setVideocardId(dto.getVideocardId());
        entity.setPowersupplyId(dto.getPowersupplyId());
        entity.setCoverId(dto.getCoverId());
        return entity;
    }
}
