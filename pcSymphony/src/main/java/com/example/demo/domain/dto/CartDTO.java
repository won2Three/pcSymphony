package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Integer cartId;
    private Integer cpuId;
    private Integer cpuCoolerId;
    private Integer motherboardId;
    private Integer memoryId;
    private Integer storageId;
    private Integer videocardId;
    private Integer powersupplyId;
    private Integer coverId;
    private String userId;
}
