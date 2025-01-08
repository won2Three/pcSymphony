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

    //----------------------------------------------호환성 검사----------------------------------------------

    //호환성 검사 공통 엔드포인트
    @GetMapping("/check-compatibility")
    public ResponseEntity<Map<String, Object>> checkCompatibility() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserName = authentication.getName();

        // 장바구니 정보 가져오기
        CartEntity cart = cartRepository.findByUser_MemberId(loggedInUserName);

        // 호환성 체크할 부품 목록
        Map<String, Boolean> compatibilityResults = new HashMap<>();

        // 각 부품이 존재하면 호환성 체크
        if (cart.getCpu() != null && cart.getMotherboard() != null) {
            boolean cpuMotherboardCompatible = cartService.checkCpuMotherboardCompatibility(cart);
            compatibilityResults.put("cpuMotherboardCompatibility", cpuMotherboardCompatible);
        }

        if (cart.getMotherboard() != null && cart.getMemory() != null) {
            boolean motherboardMemoryCompatible = cartService.checkMotherboardMemoryCompatibility(cart);
            compatibilityResults.put("motherboardMemoryCompatibility", motherboardMemoryCompatible);
        }

        // 필요한 다른 호환성 검사 추가 가능 (예: GPU, PSU 등)

        // 전체 호환성 결과
        boolean isCompatible = compatibilityResults.values().stream().allMatch(Boolean::booleanValue);

        // 호환성 검사 결과 반환
        Map<String, Object> response = new HashMap<>();
        response.put("isCompatible", isCompatible);
        response.put("compatibilityResults", compatibilityResults);

        return ResponseEntity.ok(response); // JSON 응답 반환
    }


    //Cpu랑 Motherboard 소켓 비교
    @GetMapping("/check-cpu-motherboard-compatibility")
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

        System.out.println("호환성 체크 결과: " + response);

        return ResponseEntity.ok(response);  // JSON 응답 반환
    }

    //Motherboard 타입이랑 Memory 폼팩터 비교
    @GetMapping("/check-motherboard-memory-compatibility")
    public ResponseEntity<Map<String, Object>> checkMotherboardMemoryCompatibility() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserName = authentication.getName();

        CartEntity cart = cartRepository.findByUser_MemberId(loggedInUserName);
        boolean isCompatible = cartService.checkMotherboardMemoryCompatibility(cart);

        Map<String, Object> response = new HashMap<>();
        response.put("isCompatible", isCompatible);

        //호환성 체크 세부 사항
        response.put("motherboardCompatibility", isCompatible && cart.getMotherboard().getMotherboardMemoryType().equals(cart.getMemory().getMemoryFormFactor()));
        response.put("memoryCompatibility", isCompatible && cart.getMemory().getMemoryFormFactor().equals(cart.getMotherboard().getMotherboardMemoryType()));

        return ResponseEntity.ok(response);
    }

    // Cpu타입이랑 Memory비교
    @PostMapping("/check-cpu-memory-compatibility")
    public ResponseEntity<Map<String, Object>> checkCpuMemoryCompatibility(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User is not authenticated."));
        }

        String userId = principal.getName();
        CartEntity cart = cartRepository.findByUser_MemberId(userId);

        if (cart == null || cart.getCpu() == null || cart.getMemory() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Incomplete cart data for compatibility check."));
        }

        boolean isCompatible = cartService.checkCpuMemoryCompatibility(cart);

        return ResponseEntity.ok(Map.of(
                "isCompatible", isCompatible,
                "cpuName", cart.getCpu().getName(),
                "memoryType", cart.getMemory().getMemoryFormFactor()
        ));
    }



}
