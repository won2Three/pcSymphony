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
@Table(name = "cpucooler")
public class CpuCoolerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cpucooler_id")
    private Integer id;

    @Column(name = "cpucooler_name", nullable = false, length = 255)
    private String name;

    @Column(name = "cpucooler_manufacturer", nullable = false, length = 50)
    private String manufacturer;

    @Column(name = "cpucooler_part", nullable = false, length = 50)
    private String part;

    @Column(name = "cpucooler_price")
    private Double price;

    @Column(name = "cpucooler_cpu_socket", length = 20)
    private String cpuCoolerCpuSocket;

    @Column(name = "cpucooler_Water_cooled")
    private Boolean cpuCoolerWaterCooled;

    @Column(name = "cpucooler_color", length = 20)
    private String cpuCoolerColor;

    @Column(name = "cpucooler_noise_level")
    private Integer cpuCoolerNoiseLevel;

    @Column(name = "cpucooler_fan_rpm", length = 20)
    private String cpuCoolerFanRpm;

}
