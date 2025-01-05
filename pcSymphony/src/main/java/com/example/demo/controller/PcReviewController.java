package com.example.demo.controller;

import com.example.demo.domain.dto.PcReviewDTO;
import com.example.demo.domain.entity.CartEntity;
import com.example.demo.domain.entity.PartsReviewEntity;
import com.example.demo.domain.entity.PcReviewEntity;
import com.example.demo.repository.CartRepository;

import com.example.demo.repository.PartsReviewRepository;
import com.example.demo.repository.PcReviewRepository;
import com.example.demo.security.MemberUserDetails;
import com.example.demo.service.CartService;
import com.example.demo.service.PartsReviewService;
import com.example.demo.service.PcReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

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
    private final PartsReviewRepository partsReviewRepository;



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
                partsReviewEntity.setPartsreviewTitle(review.getTitle());
                partsReviewEntity.setPartsreviewContent(review.getContent());
                partsReviewEntity.setPartsreviewDate(LocalDateTime.now());
                partsReviewEntity.setPartsreviewRating(review.getRating());

                partsReviewEntity.setUserId(userName);

                // partId를 적절한 필드에 설정
                switch (partName) {
                    case "cpu" -> {
                        partsReviewEntity.setCpuId(partId);
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsreviewId();
                        // partsReviewId를 CPU 리뷰 ID에 저장
                        pcReviewDTO.setCpuReviewId(partsReviewId);
                        log.info("Saved CPU Review ID: {}", partsReviewId);
                    }
                    case "cpucooler" -> {
                        partsReviewEntity.setCpucoolerId(partId);
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsreviewId();
                        // partsReviewId를 CPU Cooler 리뷰 ID에 저장
                        pcReviewDTO.setCpucoolerReviewId(partsReviewId);
                        log.info("Saved CPU Cooler Review ID: {}", partsReviewId);
                    }
                    case "motherboard" -> {
                        partsReviewEntity.setMotherboardId(partId);
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsreviewId();
                        // partsReviewId를 Motherboard 리뷰 ID에 저장
                        pcReviewDTO.setMotherboardReviewId(partsReviewId);
                        log.info("Saved Motherboard Review ID: {}", partsReviewId);
                    }
                    case "memory" -> {
                        partsReviewEntity.setMemoryId(partId);
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsreviewId();
                        // partsReviewId를 Memory 리뷰 ID에 저장
                        pcReviewDTO.setMemoryReviewId(partsReviewId);
                        log.info("Saved Memory Review ID: {}", partsReviewId);
                    }
                    case "storage" -> {
                        partsReviewEntity.setStorageId(partId);
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsreviewId();
                        // partsReviewId를 Storage 리뷰 ID에 저장
                        pcReviewDTO.setStorageReviewId(partsReviewId);
                        log.info("Saved Storage Review ID: {}", partsReviewId);
                    }
                    case "videocard" -> {
                        partsReviewEntity.setVideocardId(partId);
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsreviewId();
                        // partsReviewId를 VideoCard 리뷰 ID에 저장
                        pcReviewDTO.setVideocardReviewId(partsReviewId);
                        log.info("Saved VideoCard Review ID: {}", partsReviewId);
                    }
                    case "powersupply" -> {
                        partsReviewEntity.setPowersupplyId(partId);
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsreviewId();
                        // partsReviewId를 PowerSupply 리뷰 ID에 저장
                        pcReviewDTO.setPowersupplyReviewId(partsReviewId);
                        log.info("Saved PowerSupply Review ID: {}", partsReviewId);
                    }
                    case "cover" -> {
                        partsReviewEntity.setCoverId(partId);
                        PartsReviewEntity savedEntity = partsReviewRepository.save(partsReviewEntity);
                        Integer partsReviewId = savedEntity.getPartsreviewId();
                        // partsReviewId를 Cover 리뷰 ID에 저장
                        pcReviewDTO.setCoverReviewId(partsReviewId);
                        log.info("Saved Cover Review ID: {}", partsReviewId);
                    }
                }
            }


        });

        pcReviewService.savePcReview(pcReviewDTO);


        return "redirect:/pcReview/pcReviewlist";
    }

}
