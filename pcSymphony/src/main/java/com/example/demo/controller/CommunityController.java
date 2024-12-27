package com.example.demo.controller;

import com.example.demo.domain.dto.CommunityDTO;
import com.example.demo.domain.entity.CommunityEntity;
import com.example.demo.security.MemberUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.CommunityService;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("community")
@Controller
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("list")
    public String list() {
        return "community/list";
    }

    @GetMapping("write")
    public String write() {
        return "community/write";
    }

    @PostMapping("write")
    public String write(
            CommunityDTO community,
            @AuthenticationPrincipal MemberUserDetails user) {
        community.setMemberId(user.getUsername());
        communityService.write(community);
        return "redirect:list";
    }

//    @GetMapping("read")
//    public String read(@RequestParam("communityId") int communityId,
//                       Model model) {
//        CommunityDTO communityDto = communityService.getCommunity(communityId);
//        model.addAttribute("community", communityDto);
//        return "community/read";
//    }
    @GetMapping("read")
    public String read(@RequestParam("communityId") int communityId,
                       Model model) {

        //조회수 증가
        communityService.incrementViewCount(communityId);

        CommunityDTO communityDto = communityService.getCommunity(communityId);
        model.addAttribute("community", communityDto);
        return "community/read";
    }


    @PostMapping("delete")
    public String delete(@RequestParam("communityId") Integer communityId,
                         @AuthenticationPrincipal MemberUserDetails user) {
        communityService.delete(communityId, user.getUsername());
        return "redirect:list";
    }

//    @GetMapping("update/{id}")
//    public String update(@PathVariable("id") Integer id, Model model) {
//        CommunityDTO communityDto = communityService.getCommunity(id);
//        model.addAttribute("community",communityDto);
//        return "community/update";
//    }

//    @GetMapping("update/{id}")
//    public String update(@PathVariable("id") Integer id, Model model) {
//        CommunityDTO communityDto = communityService.getCommunity(id); // DTO 객체 가져오기
//        model.addAttribute("community", communityDto); // 모델에 community 추가
//        return "community/update"; // 업데이트 페이지로 리턴
//    }
//
//    @PostMapping("update/{id}")
//    public String update(@PathVariable("id") Integer id, CommunityEntity entity, String username) {
//        //기존에 담겨져 있던 글
//        CommunityDTO communityDto = communityService.getCommunity(id);
////        CommunityDTO communityDto = new CommunityDTO();
//        //기존에 있던 내용에 덮어씌우기
//        communityDto.setCommunityId(id);
//        communityDto.setCommunityTitle(entity.getCommunityTitle());
//        communityDto.setCommunityContent(entity.getCommunityContent());
//
//        communityService.update(id, communityDto, username);
//        return "redirect:/community/list";
//
//    }
@GetMapping("update/{id}")
public String updateForm(@PathVariable("id") Integer id, Model model, Principal principal) {
    String currentUsername = principal.getName();

    CommunityDTO communityDto = communityService.getCommunity(id);

    // 작성자가 아니라면 접근 제한
    if (!communityDto.getMemberId().equals(currentUsername)) {
        // 수정 권한이 없으면 현재 게시글 페이지로 리디렉션
        model.addAttribute("errorMessage", "수정 권한이 없습니다.");
        return "redirect:/community/read?communityId=" + id;  // 리디렉션
    }

    model.addAttribute("community", communityDto);
    return "community/update"; // 업데이트 페이지
}


    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, CommunityEntity entity, Principal principal) {
        // 현재 사용자 확인
        String currentUsername = principal.getName();

        // 기존에 있던 글 가져오기
        CommunityDTO communityDto = communityService.getCommunity(id);

        // 기존 내용에 덮어쓰기
        communityDto.setCommunityId(id);
        communityDto.setCommunityTitle(entity.getCommunityTitle());
        communityDto.setCommunityContent(entity.getCommunityContent());

        // 게시글 수정
        communityService.update(id, communityDto, currentUsername);

        return "redirect:/community/list";
    }



}
