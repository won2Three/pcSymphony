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
@Table(name = "cover")
public class CoverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cover_id")
    private Integer id;

    @Column(name = "cover_name", nullable = false, length = 50)
    private String name;

    @Column(name = "cover_manufactor", nullable = false, length = 50)
    private String manufacturer;

    @Column(name = "cover_part", nullable = false, length = 50)
    private String part;

    @Column(name = "cover_price")
    private Integer price;

    @Column(name = "cover_power_supply")
    private Boolean coverPowerSupply;

    @Column(name = "cover_motherboard_Form_Factor", nullable = false, length = 50)
    private String coverMotherboardFormFactor;

    @Column(name = "cover_Maximum_Video_Card_Length", nullable = false, length = 50)
    private String coverMaxVideoCardLength;

    @Column(name = "cover_type", nullable = false, length = 50)
    private String coverType;

    @Column(name = "cover_color", nullable = false, length = 20)
    private String coverColor;
}
