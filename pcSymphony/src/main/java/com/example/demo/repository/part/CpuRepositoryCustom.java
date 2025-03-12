package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.CpuEntity;
import java.util.List;

public interface CpuRepositoryCustom {
    List<CpuEntity> findCompatibleCpusByMotherboard(String motherboardSocket);
    List<CpuEntity> findCompatibleCpusByMemory(String memoryType, String alternativeType, String thirdType);
    List<CpuEntity> findCompatibleCpusByMotherboardAndMemory(String motherboardSocket, String memoryType, String alternativeType, String thirdType);
}