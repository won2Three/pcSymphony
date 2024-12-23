package com.example.demo.domain.dto.part;;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotherboardDTO {

    private Integer id;
    private String name;
    private String manufacturer;
    private String part;
    private Integer price;
    private String motherboardSocketCpu;
    private String motherboardFormFactor;
    private String motherboardMemoryType;
    private String motherboardChipset;
    private Integer motherboardMemorySlotCount;
    private Integer motherboardMdot2SlotCount;
    private Integer motherboardMemoryMax;
    private String motherboardColor;
}
