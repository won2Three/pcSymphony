package com.example.demo.service;

import com.example.demo.domain.dto.MemberDto;
import com.example.demo.domain.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    //암호화 인코더
    private final BCryptPasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    public void join(MemberDto dto) {
        MemberEntity entity = MemberEntity.builder()
                .memberId(dto.getMemberId())
                .memberPw(passwordEncoder.encode(dto.getMemberPw()))
                .memberName(dto.getMemberName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .gender(dto.getGender())
                .build();


        memberRepository.save(entity);


    }
}
