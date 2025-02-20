package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommunityDTO {

    //작성자아이디
    String memberId;
    //글순서
    int communityId;
    String communityTitle;
    String communityContent;
    int communityView;
    LocalDateTime communityDate;
    String imagePath;

}
