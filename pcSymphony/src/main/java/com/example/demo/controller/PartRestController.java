package com.example.demo.controller;

import com.example.demo.domain.dto.CommunityReplyDTO;
import com.example.demo.domain.dto.part.PartsReviewDTO;
import com.example.demo.security.MemberUserDetails;
import com.example.demo.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("part")
@RequiredArgsConstructor
public class PartRestController {

    private final PartService partService;

    //리뷰 저장
    @PostMapping("partsReviewWrite")
    public ResponseEntity<?> partsReviewWrite(@RequestBody PartsReviewDTO reviewDTO,
                                              @AuthenticationPrincipal MemberUserDetails user) {
        System.out.println("Received reviewDTO: " + reviewDTO); // 디버깅용 출력
        reviewDTO.setMemberId(user.getUsername());
        partService.partsReviewWrite(reviewDTO);

        // JSON 응답 반환
        Map<String, String> response = new HashMap<>();
        response.put("message", "리뷰 저장 성공");

        return ResponseEntity.ok(response);
    }

    // 리뷰 목록 조회 API
    @GetMapping("/{partType}/{id}/reviews")
    public ResponseEntity<?> getReviewList(@PathVariable("partType") String partType,
                                           @PathVariable("id") Integer id) {
        List<PartsReviewDTO> reviewList = partService.getReviewList(partType, id);
        return ResponseEntity.ok(reviewList);
    }

    //리뷰 삭제
    @PostMapping("partsReviewDelete")
    public ResponseEntity<?> reviewDelete(@RequestBody PartsReviewDTO reviewDTO,
                                         @AuthenticationPrincipal MemberUserDetails userDetails) {
        partService.partsReviewDelete(reviewDTO.getPartsReviewId(), userDetails.getUsername());
        return ResponseEntity.ok().build();

    }
}
