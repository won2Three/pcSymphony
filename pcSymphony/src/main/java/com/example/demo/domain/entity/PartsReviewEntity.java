package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "partsreview")
public class PartsReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer partsreviewId;

    private Integer partsreviewRating;

    @Column(nullable = false, length = 100)
    private String partsreviewTitle;

    @Column(nullable = false, length = 2000)
    private String partsreviewContent;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime partsreviewDate;

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
