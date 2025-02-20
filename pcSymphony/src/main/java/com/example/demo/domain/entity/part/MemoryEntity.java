package com.example.demo.domain.entity.part;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "memory")
public class MemoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memory_id")
    private Integer id;

    @Column(name = "memory_name", nullable = false, length = 255)
    private String name;

    @Column(name = "memory_manufacturer", nullable = false, length = 50)
    private String manufacturer;

    @Column(name = "memory_price")
    private Double price;

    @Column(name = "memory_form_factor", nullable = false, length = 50)
    private String memoryFormFactor;

    @Column(name = "memory_size")
    private Integer memorySize;

    @Column(name = "memory_speed", length = 100)
    private String memorySpeed;

    @Column(name = "memory_image_url", length = 1000)
    private String imageUrl; // CPU 이미지 경로 필드
}
