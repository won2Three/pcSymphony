package com.example.demo.part.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpuCoolerDTO {

    private Integer id;
    private String name;
    private String manufacturer;
    private String part;
    private Integer price;
    private String cpuCoolerCpuSocket;
    private Boolean cpuCoolerWaterCooled;
    private String cpuCoolerColor;
    private Integer cpuCoolerNoiseLevel;
    private String cpuCoolerFanRpm;
}
