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
import java.text.DecimalFormat;
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

        // DecimalFormat을 사용하여 가격 포맷팅 (천 단위 쉼표 추가)
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(totalPrice);  // 쉼표가 포함된 가격으로 포맷

        // 포맷된 가격을 모델에 추가
        model.addAttribute("formattedPrice", formattedPrice);

        return "cart/cart";
    }

}