package com.example.demo.controller;

import com.example.demo.domain.dto.CommunityDTO;
import com.example.demo.domain.dto.CommunityReplyDTO;
import com.example.demo.security.MemberUserDetails;
import com.example.demo.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("community")
@RequiredArgsConstructor
public class CommunityRestController {

    private final CommunityService communityService;

    @PostMapping("getList")
    public List<CommunityDTO> getList() {
        return communityService.getList();
    }

    //커뮤니티 댓글
    //저장
    @PostMapping("communityReplyWrite")
    public ResponseEntity<?> communityReplyWrite(@RequestBody CommunityReplyDTO replyDTO,
                                      @AuthenticationPrincipal MemberUserDetails user) {
        replyDTO.setMemberId(user.getUsername());
        communityService.communityReplyWrite(replyDTO);

        return ResponseEntity.ok().build();
    }

    //커뮤니티 댓글 목록 조회
    @PostMapping("communityReplyList")
    public ResponseEntity<?> getReplyList(@RequestParam("communityId") int communityId) {

        // 댓글 목록을 가져오는 서비스 메서드를 호출
        List<CommunityReplyDTO> replyList = communityService.getReplyList(communityId);

        // 댓글 목록을 ResponseEntity로 반환
        return ResponseEntity.ok(replyList);
    }

    //커뮤니티 댓글 삭제
    @PostMapping("communityReplyDelete")
    public void replyDelete(CommunityReplyDTO replyDTO,
                            @AuthenticationPrincipal MemberUserDetails userDetails) {

    }


}
