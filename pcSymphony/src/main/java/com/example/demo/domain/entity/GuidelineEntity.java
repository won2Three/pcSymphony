package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "guideline")
public class GuidelineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guideline_id")
    private int guidelineId;

    @Column(name = "guideline_title", length = 100, nullable = false)
    private String guidelineTitle;

    @Column(name = "guideline_content", length = 10000, nullable = false)
    private String guidelineContent;

    @Column(name = "cpu_id")
    private int cpuId;

    @Column(name = "cpucooler_id")
    private int cpuCoolerId;

    @Column(name = "motherboard_id")
    private int motherboardId;

    @Column(name = "memory_id")
    private int memoryId;

    @Column(name = "storage_id")
    private int storageId;

    @Column(name = "videocard_id")
    private int videoCardId;

    @Column(name = "powersupply_id")
    private int powerSupplyId;

    @Column(name = "cover_id")
    private int coverId;

    @Column(name = "image_path")
    private String imagePath;
}
