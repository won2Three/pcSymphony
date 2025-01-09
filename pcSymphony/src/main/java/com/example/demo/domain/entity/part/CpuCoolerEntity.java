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

    @Column(name = "cpucooler_price")
    private Double price;

    @Column(name = "cpucooler_motherboard_sockets", length = 1000)
    private String cpucoolerMotherboardSockets;

    @Column(name = "cpucooler_height")
    private double cpuCoolerHeight;

}
