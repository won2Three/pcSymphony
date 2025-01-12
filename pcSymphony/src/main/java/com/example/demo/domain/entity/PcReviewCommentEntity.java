package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pcreviewcomment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PcReviewCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pcreviewcomment_id")
    private int pcreviewCommentId;

    @Column(name = "pcreviewcomment_content", nullable = false, length = 2000)
    private String pcreviewCommentContent;

    @Column(name = "pcreviewcomment_date", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime pcreviewCommentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pcreview_id", referencedColumnName = "pcreview_id", nullable = false)
    private PcReviewEntity pcReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private MemberEntity user;
}
