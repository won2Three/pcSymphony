package com.example.demo.controller;

import com.example.demo.domain.dto.CommunityReplyDTO;
import com.example.demo.domain.dto.PartsReviewDTO;
import com.example.demo.domain.entity.part.PartsReviewEntity;
import com.example.demo.repository.part.PartsReviewRepository;
import com.example.demo.security.MemberUserDetails;
import com.example.demo.service.PartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    private final PartsReviewRepository partsReviewRepository;

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

    //리뷰 수정
    @PutMapping("partsReviewUpdate/{id}")
    public ResponseEntity<?> updateReview(@PathVariable("id") Integer id,
                                          @RequestBody Map<String, String> requestData,
                                          @AuthenticationPrincipal MemberUserDetails userDetails) {

        String updatedTitle = requestData.get("partsReviewTitle");
        String updatedContent = requestData.get("partsReviewContent");
        if(requestData.get("partsReviewRating") == null){
            partService.partsReviewUpdate(id, updatedTitle, updatedContent, userDetails.getUsername());
        } else {
            int updatedRating = Integer.parseInt(requestData.get("partsReviewRating"));
            partService.partsReviewUpdate(id, updatedTitle, updatedContent, updatedRating, userDetails.getUsername());
        }


        //수정 처리
        // JSON 응답 반환
        Map<String, String> response = new HashMap<>();
        response.put("message", "리뷰가 수정되었습니다.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("{partType}/{id}/reviews/update")
    public ResponseEntity<?> getUpdateList(@PathVariable("partType") String partType,
                                           @PathVariable("id") Integer id) {
        List<PartsReviewDTO> reviewDTOList = partService.getReviewList(partType, id);
        return ResponseEntity.ok(reviewDTOList);
    }

    @GetMapping("partsReview/permission/{id}")
    public ResponseEntity<?> checkPermission(@PathVariable("id") Integer id,
                                             @RequestParam("action") String action,  // "edit" 또는 "delete"
                                             @AuthenticationPrincipal MemberUserDetails userDetails) {
        // 리뷰 확인
        PartsReviewEntity reviewEntity = partsReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("리뷰가 없습니다."));

        // 작성자 확인
        boolean hasPermission = reviewEntity.getMember().getMemberId().equals(userDetails.getUsername());

        if (!hasPermission) {
            return ResponseEntity.ok(Map.of("isAllowed", false));  // 권한이 없으면 false 반환
        }

        // action에 따라 권한 확인
        if ("edit".equals(action)) {
            // 수정 권한 확인 (작성자만 수정 가능)
            return ResponseEntity.ok(Map.of("isAllowed", true));  // 권한이 있으면 true 반환
        } else if ("delete".equals(action)) {
            // 삭제 권한 확인 (작성자만 삭제 가능)
            return ResponseEntity.ok(Map.of("isAllowed", true));  // 권한이 있으면 true 반환
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 action");
        }
    }
    @GetMapping("/{tableName}/list")
    public ResponseEntity<?> getPartList(
            @PathVariable String tableName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<?> partPage = partService.getPartListByPage(tableName, page, size);
        Map<String, Object> response = new HashMap<>();
        response.put("parts", partPage.getContent());
        response.put("currentPage", partPage.getNumber());
        response.put("totalPages", partPage.getTotalPages());
        response.put("totalItems", partPage.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{tableName}/search")
    public ResponseEntity<?> searchParts(
            @PathVariable String tableName,
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<?> partPage = partService.searchParts(tableName, query, page, size);
        Map<String, Object> response = new HashMap<>();
        response.put("parts", partPage.getContent());
        response.put("currentPage", partPage.getNumber());
        response.put("totalPages", partPage.getTotalPages());
        response.put("totalItems", partPage.getTotalElements());
        return ResponseEntity.ok(response);
    }
}
