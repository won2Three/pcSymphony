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


    @PostMapping("delete")
    public String delete(@RequestParam("communityId") Integer communityId,
                         @AuthenticationPrincipal MemberUserDetails user) {
        communityService.delete(communityId, user.getUsername());
        return "redirect:list";
    }

//    @GetMapping("update/{id}")
//    public String update(@PathVariable("id") Integer id, Model model) {
//        CommunityDTO communityDto = communityService.getCommunity(id); // DTO 객체 가져오기
//        model.addAttribute("community", communityDto); // 모델에 community 추가
//        return "community/update"; // 업데이트 페이지로 리턴
//    }

    @GetMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, Model model,
                         @AuthenticationPrincipal MemberUserDetails userDetails,
                         RedirectAttributes redirectAttributes) {
        // 게시글 가져오기
        CommunityDTO communityDto = communityService.getCommunity(id);

        // 사용자 확인
        if (!userDetails.getUsername().equals(communityDto.getMemberId())) {
            // 수정 권한이 없으면 에러 메시지 추가 후 read 페이지로 리다이렉트
            redirectAttributes.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
            return "redirect:/community/read?communityId=" + id; // read 페이지로 리다이렉트
        }

        model.addAttribute("community", communityDto);
        // 수정 페이지로 이동
        return "community/update"; // 수정 페이지로 이동
    }



    //기존 수정글
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

//@PostMapping("update/{id}")
//public String update(@PathVariable("id") Integer id,
//                     @ModelAttribute CommunityDTO communityDto, // DTO로 받기
//                     @AuthenticationPrincipal MemberUserDetails userDetails) { // 로그인한 사용자 정보
//
//    // 기존 게시글 가져오기
//    CommunityDTO existingCommunity = communityService.getCommunity(id);
//
//    // 사용자 확인
//    if (!userDetails.getUsername().equals(existingCommunity.getMemberId())) {
//        throw new RuntimeException("수정 권한이 없습니다.");
//    }
//
//    // 기존 게시글에 새로운 내용 덮어쓰기
//    existingCommunity.setCommunityTitle(communityDto.getCommunityTitle());
//    existingCommunity.setCommunityContent(communityDto.getCommunityContent());
//
//    // 게시글 수정 처리
//    communityService.update(id, existingCommunity, userDetails.getUsername());
//
//    return "redirect:/community/list"; // 수정 후 목록으로 리다이렉트
//}

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute CommunityDTO communityDto,
                         @AuthenticationPrincipal MemberUserDetails userDetails,
                         RedirectAttributes redirectAttributes) { // RedirectAttributes 추가

        // 기존 게시글 가져오기
        CommunityDTO existingCommunity = communityService.getCommunity(id);

        // 사용자 확인
        if (!userDetails.getUsername().equals(existingCommunity.getMemberId())) {
            // 권한이 없으면 에러 메시지 추가 후 read 페이지로 리다이렉트
            redirectAttributes.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
            return "redirect:/community/read?communityId=" + id; // read 페이지로 리다이렉트
        }

        // 기존 게시글에 새로운 내용 덮어쓰기
        existingCommunity.setCommunityTitle(communityDto.getCommunityTitle());
        existingCommunity.setCommunityContent(communityDto.getCommunityContent());

        // 게시글 수정 처리
        communityService.update(id, existingCommunity, userDetails.getUsername());

        return "redirect:/community/list"; // 수정 후 목록으로 리다이렉트
    }



}