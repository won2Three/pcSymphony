package com.example.demo.part.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoverDTO {

    private Integer id;
    private String name;
    private String manufactor;
    private String part;
    private Integer price;

    private Boolean coverPowerSupply;
    private String coverMotherboardFormFactor;
    private String coverMaxVideoCardLength;
    private String coverType;
    private String coverColor;
}
