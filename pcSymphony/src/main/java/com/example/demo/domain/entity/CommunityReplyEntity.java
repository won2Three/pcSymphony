package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "community_reply")
public class CommunityReplyEntity {

    //댓글번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_reply_id", nullable = false)
    Integer communityReplyId;

    //댓글
    @Column(name = "community_reply_content", nullable = false)
    String communityReplyContent;

    //커뮤니티 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", referencedColumnName = "community_id")
    CommunityEntity community;

    //작성자 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    MemberEntity member;

    //등록일
    @CreatedDate
    @Column(name = "community_reply_date",
            columnDefinition = "timestamp default current_timestamp")
    LocalDateTime communityReplyDate;
}
