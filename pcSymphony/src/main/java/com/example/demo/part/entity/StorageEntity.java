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
@Table(name = "storage")
public class StorageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_id")
    private Integer id;

    @Column(name = "storage_name", nullable = false, length = 50)
    private String name;

    @Column(name = "storage_manufactor", nullable = false, length = 50)
    private String manufacturer;

    @Column(name = "storage_part", nullable = false, length = 50)
    private String part;

    @Column(name = "storage_price")
    private Integer price;

    @Column(name = "storage_type", nullable = false, length = 50)
    private String storageType;

    @Column(name = "storage_form_factor", nullable = false, length = 50)
    private String storageFormFactor;

    @Column(name = "storage_capacity", length = 50)
    private String storageCapacity;

}
