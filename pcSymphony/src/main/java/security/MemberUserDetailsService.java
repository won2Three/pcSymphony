package security;

import domain.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
//유저의 정보를 가져옴
public class MemberUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException(id + " : 없는 id입니다."));

        MemberUserDetails userDetails = MemberUserDetails.builder()
                .id(memberEntity.getMemberId())
                .password(memberEntity.getMemberPw())
                .name(memberEntity.getMemberName())
                .address(memberEntity.getAddress())
                .email(memberEntity.getEmail())
                .gender(memberEntity.getGender())
                .build();

        return null;
    }
}
