package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartDTO {

    private Integer id;
    private String name;
    private String manufacturer;
    private Double price;
    private String imageUrl;

    private String coverPowerSupply;
    private String coverMotherboardFormFactor;
    private Integer coverMaxVideoCardLength;

    private String cpuCoolerMotherboardSockets;
    private Double cpuCoolerHeight;

    private String cpuSocket;
    private Integer cpuTdp;
    private Integer cpuCoreCount;
    private Integer cpuThreadCount;
    private String cpuPerformanceCoreClock;
    private String cpuPerformanceCoreBoostClock;

    private String memoryFormFactor;
    private Integer memorySize;
    private String memorySpeed;

    private String motherboardSocketCpu;
    private String motherboardFormFactor;
    private String motherboardMemoryType;
    private String motherboardChipset;
    private Integer motherboardMemorySlotCount;
    private Integer motherboardMdot2SlotCount;
    private Integer motherboardMemoryMax;

    private String powerSupplyType;
    private Integer powerSupplyWattage;

    private String storageType;
    private String storageFormFactor;
    private String storageCapacity;

    private Double videoCardLength;
    private Integer videoCardTdp;
    private Integer videoCardMemory;
    private String videoCardChipset;
    private String videoCardCoreClock;
    private String videoCardBoostClock;
}
