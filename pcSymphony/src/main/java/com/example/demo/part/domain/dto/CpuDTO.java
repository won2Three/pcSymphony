package com.example.demo.part.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpuDTO {
    private Integer id; // cpu_id 필드
    private String name; // cpu_name 필드
    private String manufacturer; // cpu_manufacturer 필드
    private String part; // cpu_part 필드
    private Integer price; // cpu_price 필드
    private String cpuSeries; // cpu_series 필드
    private String cpuSocket; // cpu_socket 필드
    private Boolean cpuIncludesCooler; // cpu_includes_cooler 필드
    private Integer cpuTdp; // cpu_tdp 필드
    private String cpuMicroarchitecture; // cpu_microarchitecture 필드
    private Integer cpuCoreCount; // cpu_core_count 필드
    private Integer cpuThreadCount; // cpu_thread_count 필드
    private Integer cpuPerformanceCoreClock; // cpu_performance_core_clock 필드
    private Integer cpuPerformanceCoreBoostClock; // cpu_performance_core_boost_clock 필드
}

