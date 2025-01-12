package com.example.demo.domain.entity.part;

import com.example.demo.domain.entity.CommunityEntity;
import com.example.demo.domain.entity.MemberEntity;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.part.CpuRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "partsreview")
public class PartsReviewEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partsreview_id", nullable = false)
    Integer partsReviewId;

    @Column(name = "partsreview_rating", nullable = false)
    int partsReviewRating;

    @Column(name = "partsreview_title", nullable = false)
    String partsReviewTitle;

    @Column(name = "partsreview_content", nullable = false)
    String partsReviewContent;

    @CreatedDate
    @Column(name = "partsreview_date",
    columnDefinition = "timestamp default current_timestamp")
    LocalDateTime partsReviewMadeDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", insertable = false, updatable = false)
    LocalDateTime partsReviewDate;

    //사용자 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    MemberEntity member;

    //파츠들 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpu_id", referencedColumnName = "cpu_id")
    CpuEntity cpu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpucooler_id", referencedColumnName = "cpucooler_id")
    CpuCoolerEntity cpucooler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motherboard_id", referencedColumnName = "motherboard_id")
    MotherboardEntity motherboard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memory_id", referencedColumnName = "memory_id")
    MemoryEntity memory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", referencedColumnName = "storage_id")
    StorageEntity storage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "videocard_id", referencedColumnName = "videocard_id")
    VideoCardEntity videocard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "powersupply_id", referencedColumnName = "powersupply_id")
    PowerSupplyEntity powersupply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_id", referencedColumnName = "cover_id")
    CoverEntity cover;

}
