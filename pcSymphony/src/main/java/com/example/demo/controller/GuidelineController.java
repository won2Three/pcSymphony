package com.example.demo.controller;

import com.example.demo.domain.dto.CommunityDTO;
import com.example.demo.domain.dto.CommunityReplyDTO;
import com.example.demo.domain.dto.GuidelineDTO;
import com.example.demo.domain.dto.PartDTO;
import com.example.demo.domain.entity.CartEntity;
import com.example.demo.domain.entity.GuidelineEntity;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.GuidelineRepository;
import com.example.demo.security.MemberUserDetails;
import com.example.demo.service.CartService;
import com.example.demo.service.CommunityService;
import com.example.demo.service.GuidelineService;
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

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("guide")
@RequiredArgsConstructor
public class GuidelineController {

    @Autowired
    private GuidelineService guidelineService;

    @Autowired
    private GuidelineRepository guidelineRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @GetMapping("/{id}")
    public String getGuidelineDetail(Model model, @PathVariable int id) {
        GuidelineEntity guidelineEntity = guidelineRepository.getReferenceById(id);
        Map<String, Object> details = guidelineService.getGuidelineDetails(id);
        model.addAttribute("details", details);

        double totalPrice = details.entrySet().stream()
                .filter(entry -> entry.getValue() instanceof PartDTO) // 값이 PartDTO인 경우만 처리
                .mapToDouble(entry -> {
                    PartDTO part = (PartDTO) entry.getValue();
                    return part.getPrice() != null ? part.getPrice() : 0.0;
                })
                .sum();

        model.addAttribute("totalPrice", totalPrice);

        if (id == 1) {
            model.addAttribute("image1", "https://www.tradeinn.com/f/13885/138853736_4/be-quiet-silent-base-601-801-%EC%B0%BD%EB%AC%B8-%EC%B8%A1%EB%A9%B4-%ED%8C%A8%EB%84%90.webp");
            model.addAttribute("image2", "https://pcper.com/wp-content/uploads/2018/09/a623-dsc-0003.jpg");
            model.addAttribute("image3", "https://img.danawa.com/prod_img/500000/105/898/img/6898105_1.jpg?shrink=360:360");
        } else if (id == 2) {
            model.addAttribute("image1", "https://www.tradeinn.com/f/13885/138853736_4/be-quiet-silent-base-601-801-%EC%B0%BD%EB%AC%B8-%EC%B8%A1%EB%A9%B4-%ED%8C%A8%EB%84%90.webp");
            model.addAttribute("image2", "https://pcper.com/wp-content/uploads/2018/09/a623-dsc-0003.jpg");
            model.addAttribute("image3", "https://img.danawa.com/prod_img/500000/105/898/img/6898105_1.jpg?shrink=360:360");
        } else if (id == 3) {
            model.addAttribute("image1", "https://www.tradeinn.com/f/13885/138853736_4/be-quiet-silent-base-601-801-%EC%B0%BD%EB%AC%B8-%EC%B8%A1%EB%A9%B4-%ED%8C%A8%EB%84%90.webp");
            model.addAttribute("image2", "https://pcper.com/wp-content/uploads/2018/09/a623-dsc-0003.jpg");
            model.addAttribute("image3", "https://img.danawa.com/prod_img/500000/105/898/img/6898105_1.jpg?shrink=360:360");
        }
        return "guide/guideline";
    }

    @GetMapping("/{id}/addToCart")
    public String guidelineTOCart(@PathVariable int id, @AuthenticationPrincipal MemberUserDetails userDetails) {
        cartService.updateCartFromGuideline(id, userDetails.getUsername());
        return "redirect:/cart";
    }
}
