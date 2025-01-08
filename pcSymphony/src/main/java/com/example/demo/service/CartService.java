package com.example.demo.service;

import com.example.demo.domain.entity.CartEntity;
import com.example.demo.domain.entity.part.CpuEntity;
import com.example.demo.domain.entity.part.MemoryEntity;
import com.example.demo.domain.entity.part.MotherboardEntity;
import com.example.demo.repository.*;
import com.example.demo.repository.part.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional
public class CartService {
    //cartRepository 주입
    private final CartRepository cartRepository;

    // 다른 레포지토리들도 주입
    private final CpuRepository cpuRepository;
    private final CpuCoolerRepository cpuCoolerRepository;
    private final MotherboardRepository motherboardRepository;
    private final MemoryRepository memoryRepository;
    private final StorageRepository storageRepository;
    private final VideoCardRepository videoCardRepository;
    private final PowerSupplyRepository powerSupplyRepository;
    private final CoverRepository coverRepository;

    // CartEntity의 가격 합계를 계산하는 메서드
    public double calculateTotalPrice(CartEntity cart) {
        // CartEntity의 모든 항목을 리스트로 모은 후, 가격을 합산
        List<Double> prices = Arrays.asList(
                cart.getCpu() != null ? cart.getCpu().getPrice() : 0.0,
                cart.getCpucooler() != null ? cart.getCpucooler().getPrice() : 0.0,
                cart.getMotherboard() != null ? cart.getMotherboard().getPrice() : 0.0,
                cart.getMemory() != null ? cart.getMemory().getPrice() : 0.0,
                cart.getStorage() != null ? cart.getStorage().getPrice() : 0.0,
                cart.getVideocard() != null ? cart.getVideocard().getPrice() : 0.0,
                cart.getPowersupply() != null ? cart.getPowersupply().getPrice() : 0.0,
                cart.getCover() != null ? cart.getCover().getPrice() : 0.0
        );

        // Stream을 사용하여 리스트의 모든 가격을 더함
        return prices.stream().mapToDouble(Double::doubleValue).sum();
    }

    // 컴포넌트를 안전하게 추가하는 공통 메서드
    private <T> void addComponentIfNotNull(Optional<T> componentOptional, List<Object> components) {
        componentOptional.ifPresent(components::add);
    }

    public List<Object> getCartComponents(CartEntity cart) {
        List<Object> components = new ArrayList<>();

        // 각 컴포넌트를 안전하게 추가
        addComponentIfNotNull(Optional.ofNullable(cart.getCpu()).map(cpu -> cpuRepository.findById(cpu.getId())), components);
        addComponentIfNotNull(Optional.ofNullable(cart.getCpucooler()).map(cpuCooler -> cpuCoolerRepository.findById(cpuCooler.getId())), components);
        addComponentIfNotNull(Optional.ofNullable(cart.getMotherboard()).map(motherboard -> motherboardRepository.findById(motherboard.getId())), components);
        addComponentIfNotNull(Optional.ofNullable(cart.getMemory()).map(memory -> memoryRepository.findById(memory.getId())), components);
        addComponentIfNotNull(Optional.ofNullable(cart.getStorage()).map(storage -> storageRepository.findById(storage.getId())), components);
        addComponentIfNotNull(Optional.ofNullable(cart.getVideocard()).map(videoCard -> videoCardRepository.findById(videoCard.getId())), components);
        addComponentIfNotNull(Optional.ofNullable(cart.getPowersupply()).map(powerSupply -> powerSupplyRepository.findById(powerSupply.getId())), components);
        addComponentIfNotNull(Optional.ofNullable(cart.getCover()).map(cover -> coverRepository.findById(cover.getId())), components);

        return components;
    }



    public CartEntity selectCart(int cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with id: " + cartId));
    }

    public CartEntity selectCartsByUserId(String userId) {
        return cartRepository.findByUser_MemberId(userId);
    }

    public boolean removeItem(String cartItem) {
        try {
            // DB에서 해당 항목을 null로 설정하거나 삭제하는 로직
            switch (cartItem) {
                case "cpu":
                    cartRepository.removeCpu();
                    break;
                case "cpucooler":
                    cartRepository.removeCpucooler();
                    break;
                case "videocard":
                    cartRepository.removeVideocard();
                    break;
                case "memory":
                    cartRepository.removeMemory();
                    break;
                case "storage":
                    cartRepository.removeStorage();
                    break;
                case "motherboard":
                    cartRepository.removeMotherboard();
                    break;
                case "powersupply":
                    cartRepository.removePowersupply();
                    break;
                case "cover":
                    cartRepository.removeCover();
                    break;
                default:
                    return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //---------------------------------호환성 서비스-----------------------------------

    // Cpu == Motherboard Socket Check
    public boolean checkCpuMotherboardCompatibility(CartEntity cart) {
        CpuEntity cpu = cart.getCpu();
        MotherboardEntity motherboard = cart.getMotherboard();

        // CPU와 Motherboard가 모두 존재할 때만 비교
        if (cpu != null && motherboard != null) {
            String cpuSocket = cpu.getCpuSocket();
            String motherboardSocket = motherboard.getMotherboardSocketCpu();
            return cpuSocket.equals(motherboardSocket);
        }
        return false; // 하나라도 없으면 호환성 검사하지 않음
    }

    // Motherboard MemoryType == Memory FormFactor Check
    public boolean checkMotherboardMemoryCompatibility(CartEntity cart) {
        MotherboardEntity motherboard = cart.getMotherboard();
        MemoryEntity memory = cart.getMemory();

        // Motherboard와 Memory가 모두 존재할 때만 비교
        if (motherboard != null && memory != null) {
            String motherboardMemoryType = motherboard.getMotherboardMemoryType();
            String memoryFormFactor = memory.getMemoryFormFactor();
            return motherboardMemoryType.equals(memoryFormFactor);
        }
        return false; // 하나라도 없으면 호환성 검사하지 않음
    }

    //Cpu == Memory
    // CPU와 메모리 호환성 확인 메서드 (CartEntity 기반)
    public boolean checkCpuMemoryCompatibility(CartEntity cart) {
        CpuEntity cpu = cart.getCpu();
        MemoryEntity memory = cart.getMemory();

        if (cpu == null || memory == null) {
            throw new IllegalArgumentException("CPU or Memory is missing in the cart.");
        }

        List<String> supportedMemoryTypes = getSupportedMemoryTypes(cpu);
        return supportedMemoryTypes.contains(memory.getMemoryFormFactor());
    }

    // CPU 엔티티 기반으로 지원 메모리 유형 반환
    private List<String> getSupportedMemoryTypes(CpuEntity cpu) {
        String manufacturer = cpu.getManufacturer();
        String name = cpu.getName();

        if ("Intel".equalsIgnoreCase(manufacturer)) {
            return getIntelSupportedMemoryTypes(name);
        } else if ("AMD".equalsIgnoreCase(manufacturer)) {
            return getAmdSupportedMemoryTypes(name);
        }
        return Collections.emptyList();
    }

    // Intel CPU의 지원 메모리 유형
    private List<String> getIntelSupportedMemoryTypes(String cpuName) {
        if (cpuName.matches(".*-13.*") || cpuName.matches(".*-12.*")) {
            return Arrays.asList("DDR4", "DDR5");
        } else if (cpuName.matches(".*-8.*|.*-9.*|.*-10.*|.*-11.*")) {
            return List.of("DDR4");
        } else if (cpuName.matches(".*-6.*|.*-7.*")) {
            return Arrays.asList("DDR3", "DDR4");
        }
        return Collections.emptyList();
    }

    // AMD CPU의 지원 메모리 유형
    private List<String> getAmdSupportedMemoryTypes(String cpuName) {
        if (cpuName.matches(".*Ryzen.*[1-5][0-9]{3}.*")) {
            return List.of("DDR4");
        } else if (cpuName.matches(".*Ryzen.*7[0-9]{3}.*")) {
            return List.of("DDR5");
        } else if (cpuName.matches(".*Threadripper.*")) {
            return List.of("DDR4");
        } else if (cpuName.matches(".*EPYC.*1.*|.*EPYC.*2.*|.*EPYC.*3.*")) {
            return List.of("DDR4");
        } else if (cpuName.matches(".*EPYC.*4.*")) {
            return List.of("DDR5");
        }
        return Collections.emptyList();
    }
    }



