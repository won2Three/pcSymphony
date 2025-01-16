package com.example.demo.service;

import com.example.demo.domain.dto.PartDTO;
import com.example.demo.repository.part.*;
import com.example.demo.domain.dto.GuidelineDTO;
import com.example.demo.domain.entity.GuidelineEntity;
import com.example.demo.repository.GuidelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuidelineService {

    private final GuidelineRepository guidelineRepository;
    private final CpuRepository cpuRepository;
    private final CpuCoolerRepository cpuCoolerRepository;
    private final MotherboardRepository motherboardRepository;
    private final MemoryRepository memoryRepository;
    private final StorageRepository storageRepository;
    private final VideoCardRepository videoCardRepository;
    private final PowerSupplyRepository powerSupplyRepository;
    private final CoverRepository coverRepository;

    // Create or Update a Guideline
    public GuidelineDTO saveOrUpdate(GuidelineDTO guidelineDTO) {
        GuidelineEntity guidelineEntity = GuidelineEntity.builder()
                .guidelineId(guidelineDTO.getGuidelineId())
                .guidelineTitle(guidelineDTO.getGuidelineTitle())
                .guidelineContent(guidelineDTO.getGuidelineContent())
                .cpuId(guidelineDTO.getCpuId())
                .cpuCoolerId(guidelineDTO.getCpuCoolerId())
                .motherboardId(guidelineDTO.getMotherboardId())
                .memoryId(guidelineDTO.getMemoryId())
                .storageId(guidelineDTO.getStorageId())
                .videoCardId(guidelineDTO.getVideoCardId())
                .powerSupplyId(guidelineDTO.getPowerSupplyId())
                .coverId(guidelineDTO.getCoverId())
                .build();

        GuidelineEntity savedEntity = guidelineRepository.save(guidelineEntity);

        return mapToDTO(savedEntity);
    }

    // Retrieve all Guidelines
    public List<GuidelineDTO> findAll() {
        return guidelineRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Retrieve a Guideline by ID
    public Optional<GuidelineDTO> findById(int guidelineId) {
        return guidelineRepository.findById(guidelineId)
                .map(this::mapToDTO);
    }

    // Delete a Guideline by ID
    public void deleteById(int guidelineId) {
        guidelineRepository.deleteById(guidelineId);
    }

    // Utility method to map Entity to DTO
    private GuidelineDTO mapToDTO(GuidelineEntity guidelineEntity) {
        return GuidelineDTO.builder()
                .guidelineId(guidelineEntity.getGuidelineId())
                .guidelineTitle(guidelineEntity.getGuidelineTitle())
                .guidelineContent(guidelineEntity.getGuidelineContent())
                .cpuId(guidelineEntity.getCpuId())
                .cpuCoolerId(guidelineEntity.getCpuCoolerId())
                .motherboardId(guidelineEntity.getMotherboardId())
                .memoryId(guidelineEntity.getMemoryId())
                .storageId(guidelineEntity.getStorageId())
                .videoCardId(guidelineEntity.getVideoCardId())
                .powerSupplyId(guidelineEntity.getPowerSupplyId())
                .coverId(guidelineEntity.getCoverId())
                .build();
    }

    public Map<String, Object> getGuidelineDetails(int guidelineId) {
        // GuidelineEntity 가져오기
        GuidelineEntity guideline = guidelineRepository.findById(guidelineId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid guideline ID: " + guidelineId));

        // 부품별 데이터 조회 및 DTO 변환
        Map<String, Object> details = new HashMap<>();

        // Guideline 정보 추가
        details.put("id", guideline.getGuidelineId());
        details.put("title", guideline.getGuidelineTitle());
        details.put("content", guideline.getGuidelineContent());

        // CPU DTO 변환
        details.put("cpu", cpuRepository.findById(guideline.getCpuId())
                .map(cpu -> PartDTO.builder()
                        .id(cpu.getId())
                        .name(cpu.getName())
                        .price(cpu.getPrice())
                        .imageUrl(cpu.getImageUrl())
                        .build())
                .orElse(null));

        // CPU Cooler DTO 변환
        details.put("cpuCooler", cpuCoolerRepository.findById(guideline.getCpuCoolerId())
                .map(cooler -> PartDTO.builder()
                        .id(cooler.getId())
                        .name(cooler.getName())
                        .price(cooler.getPrice())
                        .imageUrl(cooler.getImageUrl())
                        .build())
                .orElse(null));

        // 동일한 방식으로 다른 부품들을 추가
        details.put("motherboard", motherboardRepository.findById(guideline.getMotherboardId())
                .map(board -> PartDTO.builder()
                        .id(board.getId())
                        .name(board.getName())
                        .price(board.getPrice())
                        .imageUrl(board.getImageUrl())
                        .build())
                .orElse(null));

        details.put("memory", memoryRepository.findById(guideline.getMemoryId())
                .map(mem -> PartDTO.builder()
                        .id(mem.getId())
                        .name(mem.getName())
                        .price(mem.getPrice())
                        .imageUrl(mem.getImageUrl())
                        .build())
                .orElse(null));

        details.put("storage", storageRepository.findById(guideline.getStorageId())
                .map(stor -> PartDTO.builder()
                        .id(stor.getId())
                        .name(stor.getName())
                        .price(stor.getPrice())
                        .imageUrl(stor.getImageUrl())
                        .build())
                .orElse(null));

        details.put("videoCard", videoCardRepository.findById(guideline.getVideoCardId())
                .map(card -> PartDTO.builder()
                        .id(card.getId())
                        .name(card.getName())
                        .price(card.getPrice())
                        .imageUrl(card.getImageUrl())
                        .build())
                .orElse(null));

        details.put("powerSupply", powerSupplyRepository.findById(guideline.getPowerSupplyId())
                .map(psu -> PartDTO.builder()
                        .id(psu.getId())
                        .name(psu.getName())
                        .price(psu.getPrice())
                        .imageUrl(psu.getImageUrl())
                        .build())
                .orElse(null));

        details.put("cover", coverRepository.findById(guideline.getCoverId())
                .map(cov -> PartDTO.builder()
                        .id(cov.getId())
                        .name(cov.getName())
                        .price(cov.getPrice())
                        .imageUrl(cov.getImageUrl())
                        .build())
                .orElse(null));

        return details;
    }
}
