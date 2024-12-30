package com.example.demo.service;

import com.example.demo.domain.dto.part.PartDTO;
import com.example.demo.domain.dto.part.PartsReviewDTO;
import com.example.demo.domain.entity.MemberEntity;
import com.example.demo.domain.entity.part.*;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.part.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 게시판 서비스
 */
@RequiredArgsConstructor
@Service
@Transactional
public class PartService {

    private final CpuRepository cpuRepository;
    private final CpuCoolerRepository cpuCoolerRepository;
    private final MemoryRepository memoryRepository;
    private final StorageRepository storageRepository;
    private final MotherboardRepository motherboardRepository;
    private final CoverRepository coverRepository;
    private final VideoCardRepository videoCardRepository;
    private final PowerSupplyRepository powerSupplyRepository;
    private final MemberRepository memberRepository;
    private final PartsReviewRepository partsReviewRepository;


    // 각 테이블에 맞는 DTO를 반환하는 메서드
    public Object findPartByTableAndId(String tableName, int id) {
        Optional<?> partEntityOptional = switch (tableName) {
            case "cover" -> coverRepository.findById(id);
            case "cpucooler" -> cpuCoolerRepository.findById(id);
            case "cpu" -> cpuRepository.findById(id);
            case "memory" -> memoryRepository.findById(id);
            case "storage" -> storageRepository.findById(id);
            case "motherboard" -> motherboardRepository.findById(id);
            case "powersupply" -> powerSupplyRepository.findById(id);
            case "videocard" -> videoCardRepository.findById(id);
            default -> throw new IllegalArgumentException("Unknown table name: " + tableName);
        };

        // 엔티티가 존재하는지 확인
        Object partEntity = partEntityOptional.orElseThrow(() -> new IllegalArgumentException("Entity not found for id: " + id));

        return partEntity;

    }


    public PartDTO getPart(String tableName, int id) {
        // entity는 repository의 crud를 이용해서 담긴다
        // repository는 담을 방식을 만들어준다.
        Object entity = findPartByTableAndId(tableName, id);

        PartDTO partDTO = new PartDTO();

        // 각 엔티티에 맞는 DTO 필드 설정
        if (entity instanceof CoverEntity) {
            CoverEntity coverEntity = (CoverEntity) entity;

            partDTO.setId(coverEntity.getId());
            partDTO.setName(coverEntity.getName());
            partDTO.setManufacturer(coverEntity.getManufacturer());
            partDTO.setPart(coverEntity.getPart());
            partDTO.setPrice(coverEntity.getPrice());
            partDTO.setCoverPowerSupply(coverEntity.getCoverPowerSupply());
            partDTO.setCoverMotherboardFormFactor(coverEntity.getCoverMotherboardFormFactor());
            partDTO.setCoverMaxVideoCardLength(coverEntity.getCoverMaxVideoCardLength());
            partDTO.setCoverType(coverEntity.getCoverType());
            partDTO.setCoverColor(coverEntity.getCoverColor());

        } else if (entity instanceof CpuCoolerEntity) {
            CpuCoolerEntity cpuCoolerEntity = (CpuCoolerEntity) entity;

            partDTO.setId(cpuCoolerEntity.getId());
            partDTO.setName(cpuCoolerEntity.getName());
            partDTO.setManufacturer(cpuCoolerEntity.getManufacturer());
            partDTO.setPart(cpuCoolerEntity.getPart());
            partDTO.setPrice(cpuCoolerEntity.getPrice());
            partDTO.setCpuCoolerCpuSocket(cpuCoolerEntity.getCpuCoolerCpuSocket());
            partDTO.setCpuCoolerWaterCooled(cpuCoolerEntity.getCpuCoolerWaterCooled());
            partDTO.setCpuCoolerColor(cpuCoolerEntity.getCpuCoolerColor());
            partDTO.setCpuCoolerNoiseLevel(cpuCoolerEntity.getCpuCoolerNoiseLevel());
            partDTO.setCpuCoolerFanRpm(cpuCoolerEntity.getCpuCoolerFanRpm());

        } else if (entity instanceof CpuEntity) {
            CpuEntity cpuEntity = (CpuEntity) entity;

            partDTO.setId(cpuEntity.getId());
            partDTO.setName(cpuEntity.getName());
            partDTO.setManufacturer(cpuEntity.getManufacturer());
            partDTO.setPart(cpuEntity.getPart());
            partDTO.setPrice(cpuEntity.getPrice());
            partDTO.setCpuSeries(cpuEntity.getCpuSeries());
            partDTO.setCpuSocket(cpuEntity.getCpuSocket());
            partDTO.setCpuIncludesCooler(cpuEntity.getCpuIncludesCooler());
            partDTO.setCpuTdp(cpuEntity.getCpuTdp());
            partDTO.setCpuMicroarchitecture(cpuEntity.getCpuMicroarchitecture());
            partDTO.setCpuCoreCount(cpuEntity.getCpuCoreCount());
            partDTO.setCpuThreadCount(cpuEntity.getCpuThreadCount());
            partDTO.setCpuPerformanceCoreClock(cpuEntity.getCpuPerformanceCoreClock());
            partDTO.setCpuPerformanceCoreBoostClock(cpuEntity.getCpuPerformanceCoreBoostClock());

        } else if (entity instanceof MemoryEntity) {
            MemoryEntity memoryEntity = (MemoryEntity) entity;

            partDTO.setId(memoryEntity.getId());
            partDTO.setName(memoryEntity.getName());
            partDTO.setManufacturer(memoryEntity.getManufacturer());
            partDTO.setPart(memoryEntity.getPart());
            partDTO.setPrice(memoryEntity.getPrice());
            partDTO.setMemoryFormFactor(memoryEntity.getMemoryFormFactor());
            partDTO.setMemoryColor(memoryEntity.getMemoryColor());
            partDTO.setMemoryModule(memoryEntity.getMemoryModule());
            partDTO.setMemorySpeed(memoryEntity.getMemorySpeed());

        } else if (entity instanceof StorageEntity) {
            StorageEntity storageEntity = (StorageEntity) entity;

            partDTO.setId(storageEntity.getId());
            partDTO.setName(storageEntity.getName());
            partDTO.setManufacturer(storageEntity.getManufacturer());
            partDTO.setPart(storageEntity.getPart());
            partDTO.setPrice(storageEntity.getPrice());
            partDTO.setStorageType(storageEntity.getStorageType());
            partDTO.setStorageFormFactor(storageEntity.getStorageFormFactor());
            partDTO.setStorageCapacity(storageEntity.getStorageCapacity());

        } else if (entity instanceof MotherboardEntity) {
            MotherboardEntity motherboardEntity = (MotherboardEntity) entity;

            partDTO.setId(motherboardEntity.getId());
            partDTO.setName(motherboardEntity.getName());
            partDTO.setManufacturer(motherboardEntity.getManufacturer());
            partDTO.setPart(motherboardEntity.getPart());
            partDTO.setPrice(motherboardEntity.getPrice());
            partDTO.setMotherboardSocketCpu(motherboardEntity.getMotherboardSocketCpu());
            partDTO.setMotherboardFormFactor(motherboardEntity.getMotherboardFormFactor());
            partDTO.setMotherboardMemoryType(motherboardEntity.getMotherboardMemoryType());
            partDTO.setMotherboardChipset(motherboardEntity.getMotherboardChipset());
            partDTO.setMotherboardMemorySlotCount(motherboardEntity.getMotherboardMemorySlotCount());
            partDTO.setMotherboardMdot2SlotCount(motherboardEntity.getMotherboardMdot2SlotCount());
            partDTO.setMotherboardMemoryMax(motherboardEntity.getMotherboardMemoryMax());
            partDTO.setMotherboardColor(motherboardEntity.getMotherboardColor());

        } else if (entity instanceof PowerSupplyEntity) {
            PowerSupplyEntity powerSupplyEntity = (PowerSupplyEntity) entity;

            partDTO.setId(powerSupplyEntity.getId());
            partDTO.setName(powerSupplyEntity.getName());
            partDTO.setManufacturer(powerSupplyEntity.getManufacturer());
            partDTO.setPart(powerSupplyEntity.getPart());
            partDTO.setPrice(powerSupplyEntity.getPrice());
            partDTO.setPowerSupplyType(powerSupplyEntity.getPowerSupplyType());
            partDTO.setPowerSupplyWattage(powerSupplyEntity.getPowerSupplyWattage());
            partDTO.setPowerSupplyLength(powerSupplyEntity.getPowerSupplyLength());
            partDTO.setPowerSupplyColor(powerSupplyEntity.getPowerSupplyColor());

        } else if (entity instanceof VideoCardEntity) {
            VideoCardEntity videoCardEntity = (VideoCardEntity) entity;

            partDTO.setId(videoCardEntity.getId());
            partDTO.setName(videoCardEntity.getName());
            partDTO.setManufacturer(videoCardEntity.getManufacturer());
            partDTO.setPart(videoCardEntity.getPart());
            partDTO.setPrice(videoCardEntity.getPrice());
            partDTO.setVideoCardLength(videoCardEntity.getVideoCardLength());
            partDTO.setVideoCardTdp(videoCardEntity.getVideoCardTdp());
            partDTO.setVideoCardMemory(videoCardEntity.getVideoCardMemory());
            partDTO.setVideoCardChipset(videoCardEntity.getVideoCardChipset());
            partDTO.setVideoCardColor(videoCardEntity.getVideoCardColor());
            partDTO.setVideoCardCoreClock(videoCardEntity.getVideoCardCoreClock());
            partDTO.setVideoCardBoostClock(videoCardEntity.getVideoCardBoostClock());
        }

        return partDTO;
    }

    //-----------------------------------파츠 리뷰 ------------------------------------

    //리뷰 저장
    public void partsReviewWrite(PartsReviewDTO reviewDTO) {

        // 필수 정보 조회 (사용자 정보)
        MemberEntity memberEntity = memberRepository.findById(reviewDTO.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("사용자 정보가 없습니다"));

        // 선택적 부품들 (null 체크 후 조회)
        CpuEntity cpuEntity = (reviewDTO.getCpuId() != null)
                ? cpuRepository.findById(reviewDTO.getCpuId())
                .orElseThrow(() -> new EntityNotFoundException("CPU 정보가 없습니다."))
                : null;

        CpuCoolerEntity cpuCoolerEntity = (reviewDTO.getCpucoolerId() != null)
                ? cpuCoolerRepository.findById(reviewDTO.getCpucoolerId())
                .orElseThrow(() -> new EntityNotFoundException("cpucooler 정보가 없습니다."))
                : null;

        MemoryEntity memoryEntity = (reviewDTO.getMemoryId() != null)
                ? memoryRepository.findById(reviewDTO.getMemoryId())
                .orElseThrow(() -> new EntityNotFoundException("memory 정보가 없습니다."))
                : null;

        MotherboardEntity motherboardEntity = (reviewDTO.getMotherboardId() != null)
                ? motherboardRepository.findById(reviewDTO.getMotherboardId())
                .orElseThrow(() -> new EntityNotFoundException("motherboard 정보가 없습니다."))
                : null;

        StorageEntity storageEntity = (reviewDTO.getStorageId() != null)
                ? storageRepository.findById(reviewDTO.getStorageId())
                .orElseThrow(() -> new EntityNotFoundException("storage 정보가 없습니다."))
                : null;

        VideoCardEntity videoCardEntity = (reviewDTO.getVideocardId() != null)
                ? videoCardRepository.findById(reviewDTO.getVideocardId())
                .orElseThrow(() -> new EntityNotFoundException("videocard 정보가 없습니다."))
                : null;

        PowerSupplyEntity powerSupplyEntity = (reviewDTO.getPowersupplyId() != null)
                ? powerSupplyRepository.findById(reviewDTO.getPowersupplyId())
                .orElseThrow(() -> new EntityNotFoundException("powersupply 정보가 없습니다."))
                : null;

        CoverEntity coverEntity = (reviewDTO.getCoverId() != null)
                ? coverRepository.findById(reviewDTO.getCoverId())
                .orElseThrow(() -> new EntityNotFoundException("cover 정보가 없습니다."))
                : null;

        // 리뷰 엔티티 생성 (선택적 부품들만 포함)
        PartsReviewEntity reviewEntity = PartsReviewEntity.builder()
                .member(memberEntity)
                .partsReviewTitle(reviewDTO.getPartsReviewTitle())
                .partsReviewContent(reviewDTO.getPartsReviewContent())
                .partsReviewRating(reviewDTO.getPartsReviewRating())
                .cpu(cpuEntity) // 선택적 부품 (CPU)
                .cpucooler(cpuCoolerEntity) // 선택적 부품 (cpuCooler)
                .memory(memoryEntity) // 선택적 부품 (memory)
                .motherboard(motherboardEntity) // 선택적 부품 (motherboard)
                .storage(storageEntity) // 선택적 부품 (storage)
                .videocard(videoCardEntity) // 선택적 부품 (videocard)
                .powersupply(powerSupplyEntity) // 선택적 부품 (powersupply)
                .cover(coverEntity) // 선택적 부품 (cover)
                .partsReviewTitle(reviewDTO.getPartsReviewTitle()) // 리뷰 제목
                .partsReviewContent(reviewDTO.getPartsReviewContent()) // 리뷰 내용
                .partsReviewRating(reviewDTO.getPartsReviewRating()) // 별점
                .partsReviewDate(LocalDateTime.now()) // 리뷰 작성일
                .build();

        // 리뷰 저장
        partsReviewRepository.save(reviewEntity);
    }

    // 부품 타입에 따라 리뷰 목록 조회
    public List<PartsReviewDTO> getReviewList(String partType, Integer partId) {
        List<PartsReviewEntity> reviewEntities = null;

        switch (partType.toLowerCase()) {
            case "cpu":
                reviewEntities = partsReviewRepository.findByCpu_Id(partId);
                break;
            case "memory":
                reviewEntities = partsReviewRepository.findByMemory_Id(partId);
                break;
            case "motherboard":
                reviewEntities = partsReviewRepository.findByMotherboard_Id(partId);
                break;
            case "storage":
                reviewEntities = partsReviewRepository.findByStorage_Id(partId);
                break;
            case "videocard":
                reviewEntities = partsReviewRepository.findByVideocard_Id(partId);
                break;
            case "powersupply":
                reviewEntities = partsReviewRepository.findByPowersupply_Id(partId);
                break;
            case "cpucooler":
                reviewEntities = partsReviewRepository.findByCpucooler_Id(partId);
                break;
            case "cover":
                reviewEntities = partsReviewRepository.findByCover_Id(partId);
                break;
            default:
                throw new IllegalArgumentException("Invalid part type: " + partType);
        }
        return reviewEntities.stream()
                //단일 리뷰 엔티티
                .map(reviewEntity -> {
                    return PartsReviewDTO.builder()
                            .partsReviewId(reviewEntity.getPartsReviewId())
                            .partsReviewTitle(reviewEntity.getPartsReviewTitle())
                            .partsReviewContent(reviewEntity.getPartsReviewContent())
                            .partsReviewRating(reviewEntity.getPartsReviewRating())
                            .partsReviewDate(reviewEntity.getPartsReviewDate())
                            .memberId(reviewEntity.getMember().getMemberId())
                            .cpuId(reviewEntity.getCpu() != null ? reviewEntity.getCpu().getId() : null)
                            .cpucoolerId(reviewEntity.getCpucooler() != null ? reviewEntity.getCpucooler().getId() : null)
                            .motherboardId(reviewEntity.getMotherboard() != null ? reviewEntity.getMotherboard().getId() : null)
                            .memoryId(reviewEntity.getMemory() != null ? reviewEntity.getMemory().getId() : null)
                            .storageId(reviewEntity.getStorage() != null ? reviewEntity.getStorage().getId() : null)
                            .videocardId(reviewEntity.getVideocard() != null ? reviewEntity.getVideocard().getId() : null)
                            .powersupplyId(reviewEntity.getPowersupply() != null ? reviewEntity.getPowersupply().getId() : null)
                            .coverId(reviewEntity.getCover() != null ? reviewEntity.getCover().getId() : null)
                            .build();
                })
                .collect(Collectors.toList());
    }

    public void partsReviewDelete(Integer partsReviewId, String username) {
        PartsReviewEntity reviewEntity = partsReviewRepository.findById(partsReviewId)
                        .orElseThrow(() -> new EntityNotFoundException("리뷰 정보가 없습니다."));
        if (!username.equals(reviewEntity.getMember().getMemberId())) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        partsReviewRepository.delete(reviewEntity);
    }
}