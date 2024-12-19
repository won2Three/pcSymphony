package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommunityDto {

    //작성자아이디
    private String memberId;
    //글순서
    private int communityId;
    private String communityTitle;
    private String communityContent;
    private String communityView;
    private String communityDate;

}
