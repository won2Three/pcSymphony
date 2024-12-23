package com.example.demo.domain.entity.part;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "cpu")
public class CpuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cpu_id")  // 명시적으로 컬럼 이름을 지정
    private Integer id; // cpu_id 필드

    @Column(name = "cpu_name", nullable = false, length = 50)  // 컬럼 이름과 길이, null 허용 여부 지정
    private String name; // cpu_name 필드

    @Column(name = "cpu_manufacturer", nullable = false, length = 50) // null 허용 안 함, 길이 50
    private String manufacturer; // cpu_manufacturer 필드

    @Column(name = "cpu_part", nullable = false, length = 50) // null 허용 안 함, 길이 50
    private String part; // cpu_part 필드

    @Column(name = "cpu_price")
    private Integer price; // cpu_price 필드

    @Column(name = "cpu_series", nullable = false, length = 50)
    private String cpuSeries; // cpu_series 필드

    @Column(name = "cpu_socket", nullable = false, length = 50)
    private String cpuSocket; // cpu_socket 필드

    @Column(name = "cpu_includes_cooler", nullable = false)
    private Boolean cpuIncludesCooler; // cpu_includes_cooler 필드

    @Column(name = "cpu_tdp", nullable = false)
    private Integer cpuTdp; // cpu_tdp 필드

    @Column(name = "cpu_microarchitecture", nullable = false, length = 50)
    private String cpuMicroarchitecture; // cpu_microarchitecture 필드

    @Column(name = "cpu_core_count")
    private Integer cpuCoreCount; // cpu_core_count 필드

    @Column(name = "cpu_thread_count")
    private Integer cpuThreadCount; // cpu_thread_count 필드

    @Column(name = "cpu_performance_core_clock")
    private Integer cpuPerformanceCoreClock; // cpu_performance_core_clock 필드

    @Column(name = "cpu_performance_core_boost_clock")
    private Integer cpuPerformanceCoreBoostClock; // cpu_performance_core_boost_clock 필드
}
