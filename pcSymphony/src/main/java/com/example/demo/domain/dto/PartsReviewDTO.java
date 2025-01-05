package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartsReviewDTO {
    private Integer partsreviewId;
    private Integer partsreviewRating;
    private String partsreviewTitle;
    private String partsreviewContent;
    private String userId;
    private Integer cpuId;
    private Integer cpucoolerId;
    private Integer motherboardId;
    private Integer memoryId;
    private Integer storageId;
    private Integer videocardId;
    private Integer powersupplyId;
    private Integer coverId;
}
