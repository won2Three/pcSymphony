package com.example.demo.part.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoCardDTO {

    private Integer id;
    private String name;
    private String manufacturer;
    private String part;
    private Integer price;
    private Integer videoCardLength;
    private Integer videoCardTdp;
    private String videoCardMemory;
    private String videoCardChipset;
    private String videoCardColor;
    private Integer videoCardCoreClock;
    private Integer videoCardBoostClock;
}
