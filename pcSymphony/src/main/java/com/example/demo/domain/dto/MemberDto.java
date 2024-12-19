package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    //사용자 아이디
    String memberId;

    //비밀번호
    String memberPw;

    //사용자 이름
    String memberName;

    //이메일
    String email;

    //주소
    String address;

    //성별
    String gender;
}
