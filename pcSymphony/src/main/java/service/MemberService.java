package service;

import domain.dto.MemberDto;
import domain.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    //암호화 인코더
    private final BCryptPasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    public void join(MemberDto dto) {
        MemberEntity entity = MemberEntity.builder()
                .memberId(passwordEncoder.encode(dto.getMemberId()))
                .memberPw(dto.getMemberPw())
                .memberName(dto.getMemberName())
                .email((dto.getEmail()))
                .address(dto.getAddress())
                .gender(dto.getGender())
                .build();

        memberRepository.save(entity);


    }
}
