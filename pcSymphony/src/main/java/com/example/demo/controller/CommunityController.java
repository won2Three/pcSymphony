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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("read")
    public String read(@RequestParam("communityId") int communityId,
                       Model model) {

        //조회수 증가
        communityService.incrementViewCount(communityId);

        CommunityDTO communityDto = communityService.getCommunity(communityId);
        model.addAttribute("community", communityDto);
        return "community/read";
    }


//    @PostMapping("delete")
//    public String delete(@RequestParam("communityId") Integer communityId,
//                         @AuthenticationPrincipal MemberUserDetails user) {
//
//        communityService.delete(communityId, user.getUsername());
//        return "redirect:list";
//    }

    @PostMapping("delete")
    public String delete(@RequestParam("communityId") Integer communityId,
                         @AuthenticationPrincipal MemberUserDetails user,
                         RedirectAttributes redirectAttributes) {
        try {
            // 게시글 삭제 시도
            communityService.delete(communityId, user.getUsername());
        } catch (RuntimeException e) {
            // 삭제 권한이 없을 경우 에러 메시지 설정
            redirectAttributes.addFlashAttribute("errorMessage", "삭제 권한이 없습니다.");

            return "redirect:/community/read?communityId=" + communityId;  // 권한 오류가 발생하면 목록 페이지로 리다이렉트
        }

        // 정상적으로 삭제되었을 경우
        return "redirect:/community/list";  // 삭제 후 목록 페이지로 리다이렉트
    }


    @GetMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        CommunityDTO communityDto = communityService.getCommunity(id); // DTO 객체 가져오기
        model.addAttribute("community", communityDto); // 모델에 community 추가
        return "community/update"; // 업데이트 페이지로 리턴
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, CommunityEntity entity, String username) {
        //기존에 담겨져 있던 글
        CommunityDTO communityDto = communityService.getCommunity(id);
//        CommunityDTO communityDto = new CommunityDTO();
        //기존에 있던 내용에 덮어씌우기
        communityDto.setCommunityId(id);
        communityDto.setCommunityTitle(entity.getCommunityTitle());
        communityDto.setCommunityContent(entity.getCommunityContent());

        communityService.update(id, communityDto, username);
        return "redirect:/community/list";

    }

}
