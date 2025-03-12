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
@Table(name = "powersupply")
public class PowerSupplyEntity implements RateableProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "powersupply_id")
    private Integer id;

    @Column(name = "powersupply_name", nullable = false, length = 255)
    private String name;

    @Column(name = "powersupply_manufactor", nullable = false, length = 50)
    private String manufacturer;

    @Column(name = "powersupply_price")
    private double price;

    @Column(name = "powersupply_type", nullable = false, length = 50)
    private String powerSupplyType;

    @Column(name = "powersupply_wattage")
    private Integer powerSupplyWattage;

    @Column(name = "powersupply_image_url", length = 1000)
    private String imageUrl; // CPU 이미지 경로 필드\

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