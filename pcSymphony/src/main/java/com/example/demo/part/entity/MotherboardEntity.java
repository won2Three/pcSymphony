package com.example.demo.part.entity;

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
@Table(name = "motherboard")
public class MotherboardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "motherboard_id")
    private Integer id;

    @Column(name = "motherboard_name", nullable = false, length = 50)
    private String name;

    @Column(name = "motherboard_manufacturer", nullable = false, length = 50)
    private String manufacturer;

    @Column(name = "motherboard_part", nullable = false, length = 50)
    private String part;

    @Column(name = "motherboard_price")
    private Integer price;

    @Column(name = "motherboard_socket_cpu", nullable = false, length = 50)
    private String motherboardSocketCpu;

    @Column(name = "motherboard_form_factor", nullable = false, length = 50)
    private String motherboardFormFactor;

    @Column(name = "motherboard_memory_type", nullable = false, length = 50)
    private String motherboardMemoryType;

    @Column(name = "motherboard_chipset", length = 50)
    private String motherboardChipset;

    @Column(name = "motherboard_memory_slot_count")
    private Integer motherboardMemorySlotCount;

    @Column(name = "motherboard_m_dot_2_slot_count")
    private Integer motherboardMdot2SlotCount;

    @Column(name = "motherboard_memory_max")
    private Integer motherboardMemoryMax;

    @Column(name = "motherboard_color", length = 20)
    private String motherboardColor;

}
