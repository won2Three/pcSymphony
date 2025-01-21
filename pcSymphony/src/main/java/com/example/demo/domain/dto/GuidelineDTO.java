package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuidelineDTO {
    private int guidelineId;
    private String guidelineTitle;
    private String guidelineContent;
    private int cpuId;
    private int cpuCoolerId;
    private int motherboardId;
    private int memoryId;
    private int storageId;
    private int videoCardId;
    private int powerSupplyId;
    private int coverId;

    private String imagePath;
}
