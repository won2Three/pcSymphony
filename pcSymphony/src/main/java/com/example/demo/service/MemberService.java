package com.example.demo.service;

import com.example.demo.domain.dto.MemberDTO;
import com.example.demo.domain.entity.CartEntity;
import com.example.demo.domain.entity.MemberEntity;
import com.example.demo.repository.CartRepository;
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
    private final CartRepository cartRepository;  // CartRepository 추가

    public void join(MemberDTO dto) {
        MemberEntity entity = MemberEntity.builder()
                .memberId(dto.getMemberId())
                .memberPw(passwordEncoder.encode(dto.getMemberPw()))
                .memberName(dto.getMemberName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .gender(dto.getGender())
                .build();


        memberRepository.save(entity);

        // 카트 엔티티 생성 후 저장
        CartEntity cartEntity = CartEntity.builder()
                .user(entity)  // 해당 회원의 카트에 연결
                .build();

        // 카트 저장
        cartRepository.save(cartEntity);
    }
}
