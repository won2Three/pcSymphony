package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pcreview")
public class PcReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pcreview_id")
    private int pcreviewId;  // 리뷰 ID (Primary Key)

    @Column(name = "pcreview_title", nullable = false, length = 100)
    private String pcreviewTitle; // 리뷰 제목

    @Column(name = "pcreview_content", nullable = false, length = 2000)
    private String pcreviewContent; // 리뷰 내용

    @Column(name = "pcreview_date", nullable = false, updatable = false)
    private LocalDateTime pcreviewDate; // 작성 날짜

    @Column(name = "user_id", length = 20)
    private String userId; // 작성자 ID

    @Column(name = "cpu_review_id")
    private Integer cpuReviewId; // CPU 리뷰 ID

    @Column(name = "cpucooler_review_id")
    private Integer cpucoolerReviewId; // CPU 쿨러 리뷰 ID

    @Column(name = "motherboard_id")
    private Integer motherboardId; // 메인보드 ID

    @Column(name = "memory_review_id")
    private Integer memoryReviewId; // 메모리 리뷰 ID

    @Column(name = "storage_review_id")
    private Integer storageReviewId; // 스토리지 리뷰 ID

    @Column(name = "videocard_review_id")
    private Integer videocardReviewId; // 그래픽카드 리뷰 ID

    @Column(name = "powersupply_review_id")
    private Integer powersupplyReviewId; // 파워서플라이 리뷰 ID

    @Column(name = "cover_review_id")
    private Integer coverReviewId; // 케이스 리뷰 ID
}
