package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityReplyDTO {

    //댓글 번호
    Integer communityReplyId;

    //커뮤니티 번호
    Integer communityId;

    //작성자 ID
    String memberId;

    //댓글 내용
    String replyContent;
}
