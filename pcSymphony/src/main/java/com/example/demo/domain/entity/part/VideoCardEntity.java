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
@Table(name = "videocard")
public class VideoCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "videocard_id")
    private Integer id;

    @Column(name = "videocard_name", nullable = false, length = 50)
    private String name;

    @Column(name = "videocard_manufactor", nullable = false, length = 50)
    private String manufacturer;

    @Column(name = "videocard_part", nullable = false, length = 50)
    private String part;

    @Column(name = "videocard_price")
    private Integer price;

    @Column(name = "videocard_length")
    private Integer videoCardLength;

    @Column(name = "videocard_tdp")
    private Integer videoCardTdp;

    @Column(name = "videocard_memory", length = 50)
    private String videoCardMemory;

    @Column(name = "videocard_chipset", length = 50)
    private String videoCardChipset;

    @Column(name = "videocard_color", length = 20)
    private String videoCardColor;

    @Column(name = "videocard_core_clock")
    private Integer videoCardCoreClock;

    @Column(name = "videocard_boost_clock")
    private Integer videoCardBoostClock;

}
