package com.example.demo.controller;

import com.example.demo.domain.dto.PartsReviewDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("pcreview")
public class PcReviewController {
    @Autowired
    private final CartService cartService;

    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private final PcReviewService pcReviewService;

    @Autowired
    private final PcReviewRepository pcReviewRepository;

    @Autowired
    private final PartsReviewRepository partsReviewRepository;

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final CpuRepository cpuRepository;

    @Autowired
    private final CpuCoolerRepository cpuCoolerRepository;

    @Autowired
    private final MemoryRepository memoryRepository;

    @Autowired
    private final CoverRepository coverRepository;

    @Autowired
    private final MotherboardRepository motherboardRepository;

    @Autowired
    private final PowerSupplyRepository powerSupplyRepository;

    @Autowired
    private final StorageRepository storageRepository;

    @Autowired
    private final VideoCardRepository videoCardRepository;

    @Autowired
    private final PartsReviewService partsReviewService;



//    @Autowired
//    private final

    @GetMapping("write")
    public String write(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserName = authentication.getName(); // 로그인한 사용자 이름 가져오기 (보통 username이 저장됨)
        System.out.println(loggedInUserName);
        CartEntity cart = cartRepository.findByUser_MemberId(loggedInUserName);
        model.addAttribute("cart", cart);
        return "pcReview/pcReviewWrite";
    }

    @PostMapping("write")
    public String write(
            @ModelAttribute PcReviewDTO pcReviewDTO,
            @AuthenticationPrincipal MemberUserDetails user) {

        String userName = user.getUsername();
        pcReviewDTO.setUserId(userName);
        CartEntity cartEntity = cartRepository.findByUser_MemberId(userName);

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

        pcReviewService.savePcReview(pcReviewDTO);


        return "redirect:/pcreview/list";
    }

    @GetMapping("list")
    public String getPcReviewList(Model model) {
        List<PcReviewEntity> pcReviewList = pcReviewRepository.findAll();
//        model.addAttribute("tableName", "cpu");
//        System.out.println("test1---------------------------------------------------");
//        System.out.println(pcReviewList);
//        System.out.println("test2---------------------------------------------------");
        model.addAttribute("pcReviews", pcReviewList);
        return "pcReview/pcReviewList";
    }

    @GetMapping("/read/{id}")
    public String read(@PathVariable int id, Model model) {
        PcReviewEntity pcReviewEntity = pcReviewRepository.getReferenceById(id);
        System.out.println(pcReviewEntity);
        model.addAttribute("pcReview", pcReviewEntity);
        return "pcReview/pcReviewRead";
    }

    @PostMapping("/updateParts")
    public ResponseEntity<String> updatePartsReview(@RequestBody PartsReviewDTO request) {
        partsReviewService.updatePartsReview(request.getPartsReviewId(), request.getPartsReviewTitle(), request.getPartsReviewContent(), request.getPartsReviewRating());
        return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
    }

    @PostMapping("/updatePcReview")
    public ResponseEntity<String> updatePcReview(@RequestBody PcReviewDTO request) {
        pcReviewService.updatePcReview(request.getPcreviewId(), request.getPcreviewTitle(), request.getPcreviewContent());
        return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
    }
//
//    @PostMapping("commentWrite")
//    public ResponseEntity<?> commentWrite(@RequestBody CommunityReplyDTO replyDTO,
//                                                 @AuthenticationPrincipal MemberUserDetails user) {
//        replyDTO.setMemberId(user.getUsername());
//        pcReviewService.pcReviewCommentWrite(replyDTO);
//
//        return ResponseEntity.ok().build();
//    }



}