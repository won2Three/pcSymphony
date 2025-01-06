package com.example.demo.controller;

import com.example.demo.domain.entity.CartEntity;
import com.example.demo.domain.entity.part.*;
import com.example.demo.domain.entity.MemberEntity;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.part.*;

import com.example.demo.service.CartService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    //cart 로 이동
    @GetMapping({"", "/"})
    public String cart(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserName = authentication.getName(); // 로그인한 사용자 이름 가져오기 (보통 username이 저장됨)


        CartEntity cart = cartRepository.findByUser_MemberId(loggedInUserName);
        cartService.getCartComponents(cart);
        model.addAttribute("cart", cart);   //조회한  Entity를 모델에 추가

        double totalPrice = cartService.calculateTotalPrice(cart);
        model.addAttribute("totalPrice", totalPrice);

        return "cart/cart";
    }

    //-------------------------호환성 체크---------------------------//

    //Cpu랑 Motherboard 소켓 비교
    @GetMapping("/check-cpu-motherboard-compatibility")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkCpuMotherboardCompatibility() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserName = authentication.getName(); // 로그인한 사용자 이름 가져오기

        CartEntity cart = cartRepository.findByUser_MemberId(loggedInUserName);
        boolean isCompatible = cartService.checkCpuMotherboardCompatibility(cart); // 호환성 체크

        // 호환성 체크 결과를 Map에 담아 JSON으로 반환
        Map<String, Object> response = new HashMap<>();
        response.put("isCompatible", isCompatible);

        // 호환성 체크 세부 사항 (CPU와 Motherboard의 호환성 여부)
        response.put("cpuCompatibility", isCompatible && cart.getCpu().getCpuSocket().equals(cart.getMotherboard().getMotherboardSocketCpu()));
        response.put("motherboardCompatibility", isCompatible && cart.getCpu().getCpuSocket().equals(cart.getMotherboard().getMotherboardSocketCpu()));

        return ResponseEntity.ok(response);  // JSON 응답 반환
    }

    //Motherboard 타입이랑 Memory 폼팩터 비교
    @GetMapping("/check-motherboard-memory-compatibility")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkMotherboardMemoryCompatibility() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserName = authentication.getName();

        CartEntity cart = cartRepository.findByUser_MemberId(loggedInUserName);
        boolean isCompatible = cartService.checkMotherboardMemoryCompatibility(cart);

        Map<String, Object> response = new HashMap<>();
        response.put("isCompatible", isCompatible);

        response.put("motherboardMemoryCompatibility", isCompatible);

        return ResponseEntity.ok(response);
    }

}