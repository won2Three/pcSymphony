package com.example.demo.domain.dto;

import com.example.demo.domain.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartsReviewDTO {

    //리뷰 아이디
    Integer partsReviewId;

    //리뷰 제목
    String partsReviewTitle;

    //리뷰 내용
    String partsReviewContent;

    //별점
    int partsReviewRating;

    //리뷰 날짜
    LocalDateTime partsReviewDate;

    //작성자 아이디
    String memberId;

    //파츠들 아이디
    Integer cpuId;
    Integer cpucoolerId;
    Integer motherboardId;
    Integer memoryId;
    Integer storageId;
    Integer videocardId;
    Integer powersupplyId;
    Integer coverId;

    // Getter for member

}
