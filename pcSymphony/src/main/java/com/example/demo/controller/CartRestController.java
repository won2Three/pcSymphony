package com.example.demo.controller;

import com.example.demo.domain.entity.CartEntity;
import com.example.demo.domain.entity.MemberEntity;
import com.example.demo.domain.entity.part.*;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.part.*;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("cart")
public class CartRestController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CpuRepository cpuRepository;
    @Autowired
    private CpuCoolerRepository cpuCoolerRepository;
    @Autowired
    private MotherboardRepository motherboardRepository;
    @Autowired
    private MemoryRepository memoryRepository;
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private VideoCardRepository videoCardRepository;
    @Autowired
    private PowerSupplyRepository powerSupplyRepository;
    @Autowired
    private CoverRepository coverRepository;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody Map<String, Object> requestData, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User is not authenticated.");
        }

        MemberEntity user = memberRepository.findByMemberId(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        // 사용자에 해당하는 장바구니를 찾음
        CartEntity cartEntity = cartRepository.findByUser(user)
                .orElse(new CartEntity()); // 장바구니가 없으면 새로 생성

        cartEntity.setUser(user);

        // Save the cart entity
        String tableName = (String) requestData.get("tableName");
        String id = (String) requestData.get("id");

        System.out.print(tableName);
        System.out.print(id);

        switch (tableName) {
            case "cpu":
                CpuEntity cpu = cpuRepository.findById(Integer.parseInt(id))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid CPU ID: " + id));
                cartEntity.setCpu(cpu);
                break;
            case "cpucooler":
                CpuCoolerEntity cpuCooler = cpuCoolerRepository.findById(Integer.parseInt(id))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid CPU Cooler ID: " + id));
                cartEntity.setCpucooler(cpuCooler);
                break;
            case "motherboard":
                MotherboardEntity motherboard = motherboardRepository.findById(Integer.parseInt(id))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Motherboard ID: " + id));
                cartEntity.setMotherboard(motherboard);
                break;
            case "memory":
                MemoryEntity memory = memoryRepository.findById(Integer.parseInt(id))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Memory ID: " + id));
                cartEntity.setMemory(memory);
                break;
            case "storage":
                StorageEntity storage = storageRepository.findById(Integer.parseInt(id))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Storage ID: " + id));
                cartEntity.setStorage(storage);
                break;
            case "videocard":
                VideoCardEntity videoCard = videoCardRepository.findById(Integer.parseInt(id))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Video Card ID: " + id));
                cartEntity.setVideocard(videoCard);
                break;
            case "powersupply":
                PowerSupplyEntity powerSupply = powerSupplyRepository.findById(Integer.parseInt(id))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Power Supply ID: " + id));
                cartEntity.setPowersupply(powerSupply);
                break;
            case "cover":
                CoverEntity cover = coverRepository.findById(Integer.parseInt(id))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Cover ID: " + id));
                cartEntity.setCover(cover);
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid table name: " + tableName);
        }

        cartRepository.save(cartEntity);
        return ResponseEntity.ok("Item added to cart successfully");
    }

    @PostMapping("/removeItem")
    public ResponseEntity<Map<String, Object>> removeItem(@RequestBody Map<String, String> request) {
        String cartItem = request.get("cartItem");

        boolean success = cartService.removeItem(cartItem);  // 서비스에서 해당 항목을 삭제하는 로직
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);

        return ResponseEntity.ok(response);
    }
}
