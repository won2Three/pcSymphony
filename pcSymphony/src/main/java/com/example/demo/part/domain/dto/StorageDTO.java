package com.example.demo.part.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageDTO {

    private Integer id;
    private String name;
    private String manufacturer;
    private String part;
    private Integer price;
    private String storageType;
    private String storageFormFactor;
    private String storageCapacity;
}
