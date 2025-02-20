package com.example.demo.controller;

import com.example.demo.domain.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.service.MemberService;

@RequiredArgsConstructor
@Controller
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("loginForm")
    public String loginForm() {
        return "member/loginForm";
    }

    @GetMapping("join")
    public String join() {
        return "member/joinForm";
    }

    /**
     *
     * @param memberDto 회원가입 폼에서 제출된 데이터를 받기 위한 파라미터
     * memberService 실제로 회원가입을 처리하는 메서드
     * @return 회원가입 후 로그인페이지로 리다이렉트
     **/
    @PostMapping("join")
    public String join(MemberDTO memberDto) {
        memberService.join(memberDto);
        return "redirect:/member/loginForm";
    }

    @PostMapping("login")
    public String login(MemberDTO memberDto) {
        return "redirect:/";
    }
}
