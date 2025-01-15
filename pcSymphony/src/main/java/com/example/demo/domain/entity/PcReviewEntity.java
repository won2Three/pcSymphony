package com.example.demo.domain.entity;

import com.example.demo.domain.entity.part.PartsReviewEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

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

//    @Column(name = "pcreview_date", nullable = false, updatable = false)
//    private LocalDateTime pcreviewMadeDate; // 작성 날짜
//    private LocalDateTime pcreviewMadeDate = LocalDateTime.now();

//    @LastModifiedDate
//    @Column(name = "last_modified_date", insertable = false, updatable = false)
//    private LocalDateTime pcreviewDate;

    @LastModifiedDate
    @Column(name = "pcreview_date", nullable = false)
    private LocalDateTime pcreviewDate; // 마지막 수정 날짜

    @Column(name = "user_id", length = 20)
    private String userId; // 작성자 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpu_review_id", referencedColumnName = "partsreview_id")
    private PartsReviewEntity cpuReview; // CPU 리뷰 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpucooler_review_id", referencedColumnName = "partsreview_id")
    private PartsReviewEntity cpucoolerReview; // CPU 쿨러 리뷰 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motherboard_review_id", referencedColumnName = "partsreview_id")
    private PartsReviewEntity motherboardReview; // 메인보드 리뷰 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memory_review_id", referencedColumnName = "partsreview_id")
    private PartsReviewEntity memoryReview; // 메모리 리뷰 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_review_id", referencedColumnName = "partsreview_id")
    private PartsReviewEntity storageReview; // 스토리지 리뷰 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "videocard_review_id", referencedColumnName = "partsreview_id")
    private PartsReviewEntity videocardReview; // 그래픽카드 리뷰 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "powersupply_review_id", referencedColumnName = "partsreview_id")
    private PartsReviewEntity powersupplyReview; // 파워서플라이 리뷰 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_review_id", referencedColumnName = "partsreview_id")
    private PartsReviewEntity coverReview; // 케이스 리뷰 ID
}