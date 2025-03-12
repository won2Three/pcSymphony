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
@Table(name = "cover")
public class CoverEntity implements RateableProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cover_id")
    private Integer id;

    @Column(name = "cover_name", nullable = false, length = 255)
    private String name;

    @Column(name = "cover_manufactor", nullable = false, length = 50)
    private String manufacturer;

    @Column(name = "cover_price")
    private Double price;

    @Column(name = "cover_power_supply", length = 100)
    private String coverPowerSupply;

    @Column(name = "cover_motherboard_Form_Factor", nullable = false, length = 50)
    private String coverMotherboardFormFactor;

    @Column(name = "cover_Maximum_Video_Card_Length")
    private Integer coverMaxVideoCardLength;

    @Column(name = "cover_image_url", length = 1000)
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