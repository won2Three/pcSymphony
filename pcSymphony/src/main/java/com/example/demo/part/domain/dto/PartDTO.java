package com.example.demo.part.domain.dto;

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
    private String part;
    private Integer price;

    private Boolean coverPowerSupply;
    private String coverMotherboardFormFactor;
    private String coverMaxVideoCardLength;
    private String coverType;
    private String coverColor;

    private String cpuCoolerCpuSocket;
    private Boolean cpuCoolerWaterCooled;
    private String cpuCoolerColor;
    private Integer cpuCoolerNoiseLevel;
    private String cpuCoolerFanRpm;

    private String cpuSeries;
    private String cpuSocket;
    private Boolean cpuIncludesCooler;
    private Integer cpuTdp;
    private String cpuMicroarchitecture;
    private Integer cpuCoreCount;
    private Integer cpuThreadCount;
    private Integer cpuPerformanceCoreClock;
    private Integer cpuPerformanceCoreBoostClock;

    private String memoryFormFactor;
    private String memoryColor;
    private String memoryModule;
    private Integer memorySpeed;

    private String motherboardSocketCpu;
    private String motherboardFormFactor;
    private String motherboardMemoryType;
    private String motherboardChipset;
    private Integer motherboardMemorySlotCount;
    private Integer motherboardMdot2SlotCount;
    private Integer motherboardMemoryMax;
    private String motherboardColor;

    private String powerSupplyType;
    private Integer powerSupplyWattage;
    private Integer powerSupplyLength;
    private String powerSupplyColor;

    private String storageType;
    private String storageFormFactor;
    private String storageCapacity;

    private Integer videoCardLength;
    private Integer videoCardTdp;
    private String videoCardMemory;
    private String videoCardChipset;
    private String videoCardColor;
    private Integer videoCardCoreClock;
    private Integer videoCardBoostClock;
}
