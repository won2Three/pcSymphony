package com.example.demo.service;

import com.example.demo.domain.dto.PartDTO;
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
public class PartService {

    private final CpuRepository cpuRepository;
    private final CpuCoolerRepository cpuCoolerRepository;
    private final MemoryRepository memoryRepository;
    private final StorageRepository storageRepository;
    private final MotherboardRepository motherboardRepository;
    private final CoverRepository coverRepository;
    private final VideoCardRepository videoCardRepository;
    private final PowerSupplyRepository powerSupplyRepository;

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

}