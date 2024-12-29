package com.example.demo.controller;

import com.example.demo.domain.dto.part.PartsReviewDTO;
import com.example.demo.security.MemberUserDetails;
import com.example.demo.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("part")
@RequiredArgsConstructor
public class PartRestController {

    private final PartService partService;

    //댓글 저장
    @PostMapping("partsReviewWrite")
    public ResponseEntity<?> partsReviewWrite(@RequestBody PartsReviewDTO reviewDTO,
                                              @AuthenticationPrincipal MemberUserDetails user) {
        reviewDTO.setMemberId(user.getUsername());
        partService.partsReviewWrite(reviewDTO);

        return ResponseEntity.ok().build();
    }
}
