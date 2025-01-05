package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PcReviewDTO {
    private int pcreviewId;            // 리뷰 ID (Primary Key)
    private String pcreviewTitle;     // 리뷰 제목
    private String pcreviewContent;   // 리뷰 내용
    private LocalDateTime pcreviewDate; // 작성 날짜 (timestamp)
    private String userId;            // 작성자 ID

    private Map<String, PartsReviewDTOForPcReview> partReviews;

    private Integer cpuReviewId;      // CPU 리뷰 ID
    private Integer cpucoolerReviewId; // CPU 쿨러 리뷰 ID
    private Integer motherboardReviewId;    // 메인보드 ID
    private Integer memoryReviewId;   // 메모리 리뷰 ID
    private Integer storageReviewId;  // 스토리지 리뷰 ID
    private Integer videocardReviewId; // 그래픽카드 리뷰 ID
    private Integer powersupplyReviewId; // 파워서플라이 리뷰 ID
    private Integer coverReviewId;    // 케이스 리뷰 ID
}
