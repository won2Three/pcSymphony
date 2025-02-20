package com.example.demo.controller;

import com.example.demo.domain.dto.PartsReviewDTO;
import com.example.demo.domain.dto.PcReviewCommentDTO;
import com.example.demo.domain.dto.PcReviewDTO;
import com.example.demo.domain.entity.CartEntity;
import com.example.demo.domain.entity.part.PartsReviewEntity;
import com.example.demo.domain.entity.PcReviewEntity;
import com.example.demo.repository.CartRepository;

import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.part.*;
import com.example.demo.repository.PcReviewRepository;
import com.example.demo.security.MemberUserDetails;
import com.example.demo.service.CartService;
//import com.example.demo.service.PartsReviewService;
import com.example.demo.service.PartsReviewService;
import com.example.demo.service.PcReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("pcreview")
public class PcReviewController {
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final PcReviewService pcReviewService;
    private final PcReviewRepository pcReviewRepository;
    private final PartsReviewRepository partsReviewRepository;
    private final MemberRepository memberRepository;
    private final CpuRepository cpuRepository;
    private final CpuCoolerRepository cpuCoolerRepository;
    private final MemoryRepository memoryRepository;
    private final CoverRepository coverRepository;
    private final MotherboardRepository motherboardRepository;
    private final PowerSupplyRepository powerSupplyRepository;
    private final StorageRepository storageRepository;
    private final VideoCardRepository videoCardRepository;
    private final PartsReviewService partsReviewService;

    @GetMapping("write")
    public String write(Model model, @AuthenticationPrincipal MemberUserDetails user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserName = authentication.getName(); // 로그인한 사용자 이름 가져오기 (보통 username이 저장됨)
        System.out.println(loggedInUserName);
        CartEntity cart = cartRepository.findByUser_MemberId(loggedInUserName);
        model.addAttribute("cart", cart);
        CartEntity cartEntity = cartRepository.findByUser_MemberId(user.getId());
        model.addAttribute("cart", cartEntity);
        return "pcReview/pcReviewWrite";
    }

    @PostMapping("write")
    public String write(
            @ModelAttribute PcReviewDTO pcReviewDTO,
            @RequestParam(value = "imageUpload", required = false) MultipartFile imageUpload,
            @AuthenticationPrincipal MemberUserDetails user) {

        String userName = user.getUsername();
        pcReviewDTO.setUserId(userName);
        CartEntity cartEntity = cartRepository.findByUser_MemberId(userName);

        // 이미지가 존재하면 이미지 파일을 파일 시스템에 저장
        System.out.println("테스트입니다테스트입니다테스트입니다테스트입니다테스트입니다");
        System.out.println(imageUpload);
        System.out.println("테스트입니다테스트입니다테스트입니다테스트입니다테스트입니다");
        if (imageUpload != null && !imageUpload.isEmpty()) {
            System.out.println("helllloa;lsdkfj;lsakdjf;lksadjf;lsadjf;lksdjfa");
            String imagePath = pcReviewService.saveImageToFileSystem(imageUpload); // 이미지 저장 메서드
            pcReviewDTO.setImagePath(imagePath);  // 이미지 경로 저장
        }

        pcReviewDTO.getPartReviews().forEach((partName, review) -> {
            PartsReviewEntity partsReviewEntity = new PartsReviewEntity();

            // 각 partName에 따른 ID 설정
            Integer partId = switch (partName) {
                case "cpu" -> cartEntity.getCpu() != null ? cartEntity.getCpu().getId() : null;
                case "cpucooler" -> cartEntity.getCpucooler() != null ? cartEntity.getCpucooler().getId() : null;
                case "motherboard" -> cartEntity.getMotherboard() != null ? cartEntity.getMotherboard().getId() : null;
                case "memory" -> cartEntity.getMemory() != null ? cartEntity.getMemory().getId() : null;
                case "storage" -> cartEntity.getStorage() != null ? cartEntity.getStorage().getId() : null;
                case "videocard" -> cartEntity.getVideocard() != null ? cartEntity.getVideocard().getId() : null;
                case "powersupply" -> cartEntity.getPowersupply() != null ? cartEntity.getPowersupply().getId() : null;
                case "cover" -> cartEntity.getCover() != null ? cartEntity.getCover().getId() : null;
                default -> null;
            };

            // ID가 존재하면 저장
            if (partId != null) {
//                partsReviewEntity.setPartsreviewTitle(
//                        review.getTitle() != null ? review.getTitle() : "Untitled Review"
//                );
//                partsReviewEntity.setPartsreviewContent(
//                        review.getContent() != null ? review.getContent() : "Default content"
//                );
                partsReviewEntity.setPartsReviewTitle(review.getTitle());
                partsReviewEntity.setPartsReviewContent(review.getContent());
                partsReviewEntity.setPartsReviewDate(LocalDateTime.now());
                partsReviewEntity.setPartsReviewRating(review.getRating());


                partsReviewEntity.setMember(memberRepository.findById(userName).orElse(null));
                // partId를 적절한 필드에 설정
                switch (partName) {
                    case "cpu" -> {
                        partsReviewEntity.setCpu(cpuRepository.findById(partId).orElse(null));
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsReviewId();
                        // partsReviewId를 CPU 리뷰 ID에 저장
                        pcReviewDTO.setCpuReviewId(partsReviewId);
                        log.info("Saved CPU Review ID: {}", partsReviewId);
                    }
                    case "cpucooler" -> {
                        partsReviewEntity.setCpucooler(cpuCoolerRepository.findById(partId).orElse(null));
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsReviewId();
                        // partsReviewId를 CPU Cooler 리뷰 ID에 저장
                        pcReviewDTO.setCpucoolerReviewId(partsReviewId);
                        log.info("Saved CPU Cooler Review ID: {}", partsReviewId);
                    }
                    case "motherboard" -> {
                        partsReviewEntity.setMotherboard(motherboardRepository.findById(partId).orElse(null));
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsReviewId();
                        // partsReviewId를 Motherboard 리뷰 ID에 저장
                        pcReviewDTO.setMotherboardReviewId(partsReviewId);
                        log.info("Saved Motherboard Review ID: {}", partsReviewId);
                    }
                    case "memory" -> {
                        partsReviewEntity.setMemory(memoryRepository.findById(partId).orElse(null));
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsReviewId();
                        // partsReviewId를 Memory 리뷰 ID에 저장
                        pcReviewDTO.setMemoryReviewId(partsReviewId);
                        log.info("Saved Memory Review ID: {}", partsReviewId);
                    }
                    case "storage" -> {
                        partsReviewEntity.setStorage(storageRepository.findById(partId).orElse(null));
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsReviewId();
                        // partsReviewId를 Storage 리뷰 ID에 저장
                        pcReviewDTO.setStorageReviewId(partsReviewId);
                        log.info("Saved Storage Review ID: {}", partsReviewId);
                    }
                    case "videocard" -> {
                        partsReviewEntity.setVideocard(videoCardRepository.findById(partId).orElse(null));
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsReviewId();
                        // partsReviewId를 VideoCard 리뷰 ID에 저장
                        pcReviewDTO.setVideocardReviewId(partsReviewId);
                        log.info("Saved VideoCard Review ID: {}", partsReviewId);
                    }
                    case "powersupply" -> {
                        partsReviewEntity.setPowersupply(powerSupplyRepository.findById(partId).orElse(null));
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsReviewId();
                        // partsReviewId를 PowerSupply 리뷰 ID에 저장
                        pcReviewDTO.setPowersupplyReviewId(partsReviewId);
                        log.info("Saved PowerSupply Review ID: {}", partsReviewId);
                    }
                    case "cover" -> {
                        partsReviewEntity.setCover(coverRepository.findById(partId).orElse(null));
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsReviewId();
                        // partsReviewId를 Cover 리뷰 ID에 저장
                        pcReviewDTO.setCoverReviewId(partsReviewId);
                        log.info("Saved Cover Review ID: {}", partsReviewId);
                    }
                }
            }


        });
        System.out.println("-------------------------------------");
        System.out.println(pcReviewDTO.getImagePath());
        System.out.println("-------------------------------------");
        pcReviewService.savePcReview(pcReviewDTO);


        return "redirect:/pcreview/list";
    }
//
//    @GetMapping("list")
//    public String getPcReviewList(Model model) {
//        List<PcReviewEntity> pcReviewList = pcReviewRepository.findAll();
//        model.addAttribute("pcReviews", pcReviewList);
//
//        return "pcReview/pcReviewList"; // Thymeleaf에서 렌더링할 HTML 파일 이름
//    }

    @GetMapping("/read/{id}")
    public String read(@PathVariable int id, Model model, @AuthenticationPrincipal MemberUserDetails user) {
        // 데이터 가져오기
        PcReviewEntity pcReviewEntity = pcReviewRepository.getReferenceById(id);
        CartEntity cartEntity = cartRepository.findByUser_MemberId(user.getId());

        // 날짜 포맷터
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

        // 날짜 포맷 적용
        String formattedDate = pcReviewEntity.getPcreviewDate().format(formatter);
            //파츠별 날짜 포맷
        String formattedCpuDate = pcReviewEntity.getCpuReview().getPartsReviewDate().format(formatter);
        String formattedCpucoolerDate = pcReviewEntity.getCpucoolerReview().getPartsReviewDate().format(formatter);
        String formattedMotherboardDate = pcReviewEntity.getMotherboardReview().getPartsReviewDate().format(formatter);
        String formattedMemoryDate = pcReviewEntity.getMemoryReview().getPartsReviewDate().format(formatter);
        String formattedStorageDate = pcReviewEntity.getStorageReview().getPartsReviewDate().format(formatter);
        String formattedVideocardDate = pcReviewEntity.getVideocardReview().getPartsReviewDate().format(formatter);
        String formattedPowersupplyDate = pcReviewEntity.getPowersupplyReview().getPartsReviewDate().format(formatter);
        String formattedCoverDate = pcReviewEntity.getCoverReview().getPartsReviewDate().format(formatter);

        // 모델에 추가
        model.addAttribute("cart", cartEntity);
        model.addAttribute("pcReview", pcReviewEntity);
        model.addAttribute("formattedDate", formattedDate); // 포맷된 날짜 전달
            //파츠별 날짜 포맷 모델에 추가
        model.addAttribute("formattedCpuDate", formattedCpuDate);
        model.addAttribute("formattedCpucoolerDate", formattedCpucoolerDate);
        model.addAttribute("formattedMotherboardDate", formattedMotherboardDate);
        model.addAttribute("formattedMemoryDate", formattedMemoryDate);
        model.addAttribute("formattedStorageDate", formattedStorageDate);
        model.addAttribute("formattedVideocardDate", formattedVideocardDate);
        model.addAttribute("formattedPowersupplyDate", formattedPowersupplyDate);
        model.addAttribute("formattedCoverDate", formattedCoverDate);
        return "pcReview/pcReviewRead"; // 템플릿 이름
    }


    @PostMapping("/updateParts")
    public ResponseEntity<String> updatePartsReview(@RequestBody PartsReviewDTO request) {
        partsReviewService.updatePartsReview(request.getPartsReviewId(), request.getPartsReviewTitle(), request.getPartsReviewContent(), request.getPartsReviewRating());
        return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
    }

    @PostMapping("/updatePcReview")
    public ResponseEntity<String> updatePcReview(@RequestParam("pcreviewId") String pcreviewId,
                                                 @RequestParam("pcreviewTitle") String pcreviewTitle,
                                                 @RequestParam("pcreviewContent") String pcreviewContent,
                                                 @RequestParam(value = "imageUpload", required = false) MultipartFile imageUpload) {

            pcReviewService.updatePcReview(Integer.parseInt(pcreviewId), pcreviewTitle, pcreviewContent, pcReviewService.saveImageToFileSystem(imageUpload));

        return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
    }

    @PostMapping("/updatePcReview2")
    public ResponseEntity<String> updatePcReview2(@RequestParam("pcreviewId") String pcreviewId,
                                                 @RequestParam("pcreviewTitle") String pcreviewTitle,
                                                 @RequestParam("pcreviewContent") String pcreviewContent) {

        pcReviewService.updatePcReview(Integer.parseInt(pcreviewId), pcreviewTitle, pcreviewContent);

        return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
    }

    @PostMapping("commentWrite")
    public ResponseEntity<?> commentWrite(@RequestBody PcReviewCommentDTO replyDTO,
                                          @AuthenticationPrincipal MemberUserDetails user) {
        replyDTO.setUserId(user.getUsername());
        pcReviewService.pcReviewCommentWrite(replyDTO);

        return ResponseEntity.ok().build();
    }

    // 댓글 목록 반환 메서드
    @GetMapping("/commentList")
    public ResponseEntity<List<PcReviewCommentDTO>> getPcReviewComments(@RequestParam("pcreviewId") int pcreviewId) {
        List<PcReviewCommentDTO> comments = pcReviewService.getCommentsByPcReviewId(pcreviewId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/commentDelete")
    public ResponseEntity<?> deleteComment(@RequestBody PcReviewCommentDTO commentDTO,
                                           @AuthenticationPrincipal MemberUserDetails userDetails) {
        if (!userDetails.getUsername().equals(commentDTO.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
        }

        pcReviewService.deleteComment(commentDTO.getPcreviewCommentId());
        return ResponseEntity.ok().body("댓글 삭제 성공");
    }

    @PostMapping("/delete")
    public String deletePcReview(@RequestParam("pcreviewId") int pcreviewId,
                                 Principal principal) {
        // 현재 로그인한 사용자의 ID
        String currentUserId = principal.getName();

        // 리뷰 삭제 수행
        pcReviewService.deletePcReview(pcreviewId);

        // 삭제 후 리뷰 목록 페이지로 리다이렉트
        return "redirect:/pcreview/list";
    }

    // HTML 렌더링을 위한 메서드
    @GetMapping("/list")
    public String listPage(Model model) {
        return "pcReview/pcReviewList"; // Thymeleaf 템플릿 파일
    }

    // JSON API를 위한 메서드
    @GetMapping("/api/list")
    @ResponseBody
    public ResponseEntity<Page<PcReviewDTO>> getPcReviewList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @PageableDefault(size = 10, sort = "pcreviewId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PcReviewDTO> pcReviews = (keyword == null || keyword.isEmpty())
                ? pcReviewService.getAllPcReviews(pageable)
                : pcReviewService.searchPcReviews(keyword, pageable);

        return ResponseEntity.ok(pcReviews);
    }
}