package com.example.demo.controller;

import com.example.demo.domain.dto.CommunityDTO;
import com.example.demo.domain.dto.CommunityReplyDTO;
import com.example.demo.security.MemberUserDetails;
import com.example.demo.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("community")
@RequiredArgsConstructor
public class CommunityRestController {

    private final CommunityService communityService;

//    // 게시글 목록
//    @PostMapping("getList")
//    public List<CommunityDTO> getList() {
//        return communityService.getList();
//    }

    @PostMapping("getList")
    public Page<CommunityDTO> getList(@RequestParam int page) {
        int size = 10;  // 한 페이지에 표시할 게시글 수
        return communityService.getList(page, size);
    }

    // 댓글 저장
    @PostMapping("communityReplyWrite")
    public ResponseEntity<?> communityReplyWrite(@RequestBody CommunityReplyDTO replyDTO,
                                                 @AuthenticationPrincipal MemberUserDetails user) {
        replyDTO.setMemberId(user.getUsername());
        communityService.communityReplyWrite(replyDTO);

        return ResponseEntity.ok().build();
    }

    // 댓글 목록 조회 (GET 방식으로 수정)
    @GetMapping("communityReplyList")
    public ResponseEntity<?> getReplyList(@RequestParam("communityId") int communityId) {
        // 댓글 목록을 가져오는 서비스 메서드를 호출
        List<CommunityReplyDTO> replyList = communityService.getReplyList(communityId);

        // 댓글 목록을 ResponseEntity로 반환
        return ResponseEntity.ok(replyList);
    }

    // 댓글 삭제
    @PostMapping("communityReplyDelete")
    public ResponseEntity<?> replyDelete(@RequestBody CommunityReplyDTO replyDTO,
                                         @AuthenticationPrincipal MemberUserDetails userDetails) {
        // 댓글 삭제 로직 구현
        communityService.communityReplyDelete(replyDTO.getCommunityReplyId(), userDetails.getUsername());
        return ResponseEntity.ok().build();

    }

}
