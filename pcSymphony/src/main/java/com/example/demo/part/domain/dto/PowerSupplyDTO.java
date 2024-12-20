package com.example.demo.part.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PowerSupplyDTO {

    private Integer id;
    private String name;
    private String manufacturer;
    private String part;
    private Integer price;
    private String powerSupplyType;
    private Integer powerSupplyWattage;
    private Integer powerSupplyLength;
    private String powerSupplyColor;
}
