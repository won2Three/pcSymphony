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
@Table(name = "powersupply")
public class PowerSupplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "powersupply_id")
    private Integer id;

    @Column(name = "powersupply_name", nullable = false, length = 255)
    private String name;

    @Column(name = "powersupply_manufactor", nullable = false, length = 50)
    private String manufacturer;

    @Column(name = "powersupply_part", nullable = false, length = 50)
    private String part;

    @Column(name = "powersupply_price")
    private Double price;

    @Column(name = "powersupply_type", nullable = false, length = 50)
    private String powerSupplyType;

    @Column(name = "powersupply_wattage")
    private Integer powerSupplyWattage;

    @Column(name = "powersupply_length")
    private Integer powerSupplyLength;

    @Column(name = "powersupply_color", length = 20)
    private String powerSupplyColor;

}
