package com.example.demo.domain.entity.part;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // Hibernate 프록시 무시
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "videocard")
public class VideoCardEntity implements RateableProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "videocard_id")
    private Integer id;

    @Column(name = "videocard_name", nullable = false, length = 255)
    private String name;

    @Column(name = "videocard_manufactor", nullable = false, length = 50)
    private String manufacturer;

    @Column(name = "videocard_price")
    private Double price;

    @Column(name = "videocard_length")
    private Double videoCardLength;

    @Column(name = "videocard_tdp")
    private Integer videoCardTdp;

    @Column(name = "videocard_memory")
    private Integer videoCardMemory;

    @Column(name = "videocard_chipset", length = 50)
    private String videoCardChipset;

    @Column(name = "videocard_core_clock", length = 50)
    private String videoCardCoreClock;

    @Column(name = "videocard_boost_clock", length = 50)
    private String videoCardBoostClock;

    @Column(name = "videocard_image_url", length = 1000)
    private String imageUrl; // CPU 이미지 경로 필드

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