package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PcReviewCommentDTO {
    private int pcreviewCommentId;
    private String pcreviewCommentContent;
    private LocalDateTime pcreviewCommentDate;
    private int pcreviewId; // Foreign Key to PcReview
    private String userId; // Foreign Key to User
}
