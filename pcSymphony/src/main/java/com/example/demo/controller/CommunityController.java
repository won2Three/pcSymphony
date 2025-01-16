package com.example.demo.controller;

import com.example.demo.domain.dto.CommunityDTO;
import com.example.demo.domain.entity.CommunityEntity;
import com.example.demo.security.MemberUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.CommunityService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("community")
@Controller
public class CommunityController {

    private final CommunityService communityService;

//    @GetMapping("list")
//    public String list() {
//        return "community/list";
//    }

    @GetMapping("list")
    public String list(@RequestParam(defaultValue = "0") int page, Model model) {
        int size = 10;  // 한 페이지에 표시할 게시글 수 (원하는 숫자로 조정 가능)

        // 게시글 목록과 페이지 정보 조회
        Page<CommunityDTO> communityPage = communityService.getList(page, size);

        // 모델에 페이지 정보와 게시글 목록 추가
        model.addAttribute("communityPage", communityPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", communityPage.getTotalPages());

        return "community/list";  // list.html 반환
    }

    @GetMapping("write")
    public String write() {
        return "community/write";
    }

    @PostMapping("write")
    public String write(
            CommunityDTO community,
            @RequestParam(value = "imageUpload", required = false) MultipartFile imageUpload,
            @AuthenticationPrincipal MemberUserDetails user) {

        // 이미지가 존재하면 이미지 파일을 파일 시스템에 저장
        if (imageUpload != null && !imageUpload.isEmpty()) {
            String imagePath = communityService.saveImageToFileSystem(imageUpload); // 이미지 저장 메서드
            community.setImagePath(imagePath);  // 이미지 경로 저장
        }

        community.setMemberId(user.getUsername());
        communityService.write(community);
        return "redirect:list";
    }



    @GetMapping("read")
    public String read(@RequestParam("communityId") int communityId, Model model) {
        try {
            // 조회수 증가
            communityService.incrementViewCount(communityId);

            CommunityDTO communityDto = communityService.getCommunity(communityId);

            // 커뮤니티 DTO에 이미지 경로가 잘 포함되어 있는지 확인
            log.info("Community Image Path: {}", communityDto.getImagePath());

            model.addAttribute("community", communityDto);
            return "community/read";
        } catch (Exception e) {
            log.error("Error fetching community details: ", e);
            return "error"; // error 페이지로 리다이렉트
        }
    }

    @PostMapping("delete")
    public String delete(@RequestParam("communityId") Integer communityId,
                         @AuthenticationPrincipal MemberUserDetails user) {
        communityService.delete(communityId, user.getUsername());
        return "redirect:list";
    }

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

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute CommunityDTO communityDto,
                         @RequestParam(value = "imageUpload", required = false) MultipartFile imageUpload,
                         @AuthenticationPrincipal MemberUserDetails userDetails,
                         RedirectAttributes redirectAttributes) {

        // 기존 게시글 가져오기
        CommunityDTO existingCommunity = communityService.getCommunity(id);

        // 사용자 확인 (수정 권한 확인)
        if (!userDetails.getUsername().equals(existingCommunity.getMemberId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
            return "redirect:/community/read?communityId=" + id;
        }

        // 업로드된 이미지 확인
        if (imageUpload != null && !imageUpload.isEmpty()) {
            log.info("새 이미지 업로드됨: " + imageUpload.getOriginalFilename());
        } else {
            log.info("새 이미지 없음, 기존 이미지 사용");
        }

        // 게시글 업데이트
        communityService.update(id, communityDto, userDetails.getUsername(), imageUpload);

        return "redirect:/community/read?communityId=" + id; // 수정 후 상세 페이지로 리다이렉트
    }





}