package com.example.demo.domain.entity.part;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.QueryEntity;
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

@QueryEntity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // Hibernate 프록시 무시
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "cpu")
public class CpuEntity implements RateableProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cpu_id")  // 명시적으로 컬럼 이름을 지정
    private Integer id; // cpu_id 필드

    @Column(name = "cpu_name", nullable = false, length = 255)  // 컬럼 이름과 길이, null 허용 여부 지정
    private String name; // cpu_name 필드

    @Column(name = "cpu_manufacturer", nullable = false, length = 50) // null 허용 안 함, 길이 50
    private String manufacturer; // cpu_manufacturer 필드

    @Column(name = "cpu_price")
    private Double price; // cpu_price 필드

    @Column(name = "cpu_socket", nullable = false, length = 50)
    private String cpuSocket; // cpu_socket 필드

    @Column(name = "cpu_tdp", nullable = false)
    private Integer cpuTdp; // cpu_tdp 필드

    @Column(name = "cpu_core_count")
    private Integer cpuCoreCount; // cpu_core_count 필드

    @Column(name = "cpu_thread_count")
    private Integer cpuThreadCount; // cpu_thread_count 필드

    @Column(name = "cpu_performance_core_clock", length = 50)
    private String cpuPerformanceCoreClock; // cpu_performance_core_clock 필드

    @Column(name = "cpu_performance_core_boost_clock", length = 50)
    private String cpuPerformanceCoreBoostClock; // cpu_performance_core_boost_clock 필드

    @Column(name = "cpu_image_url", length = 1000)
    private String imageUrl; // CPU 이미지 경로 필드

    // 평균 별점
    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Override
    public Double getAverageRating() {
        return this.averageRating;
    }

    @Override
    public Integer getReviewCount() {
        return this.reviewCount;
    }
}