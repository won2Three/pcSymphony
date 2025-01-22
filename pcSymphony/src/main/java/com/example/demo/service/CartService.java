package com.example.demo.service;

import com.example.demo.domain.entity.CartEntity;
import com.example.demo.domain.entity.GuidelineEntity;
import com.example.demo.domain.entity.part.*;
import com.example.demo.repository.*;
import com.example.demo.repository.part.*;
import jakarta.persistence.EntityNotFoundException;
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
    private final GuidelineRepository guidelineRepository;

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
            System.out.println("test--------------------------------------------------");
            System.out.println("motherboardSocketCpu" + motherboard.getMotherboardSocketCpu());
            System.out.println("cpuSocket" + cpu.getCpuSocket());
            System.out.println("test--------------------------------------------------");
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

            String memoryTypeFromFormFactor = memoryFormFactor.split("-")[0];

            System.out.println("test--------------------------------------------------");
            System.out.println("motherboardMemoryType" + motherboard.getMotherboardMemoryType());
            System.out.println("memoryFormFactor" + memory.getMemoryFormFactor());
            System.out.println("memoryTypeFromFormFactor" + memoryTypeFromFormFactor);
            System.out.println("test--------------------------------------------------");

            // 비교 후 결과 출력
            boolean isCompatible = motherboardMemoryType.equals(memoryTypeFromFormFactor);
            System.out.println("호환성 검사 결과: " + isCompatible);
            return isCompatible;
        }

        return false; // 하나라도 없으면 호환성 검사하지 않음
    }

    //Cpu == Memory
    public boolean checkCpuMemoryCompatibility(CartEntity cart) {
        CpuEntity cpu = cart.getCpu();
        MemoryEntity memory = cart.getMemory();

        if (cpu == null || memory == null) {
            throw new IllegalArgumentException("CPU or Memory is missing in the cart.");
        }

        String memoryTypeFromFormFactor = memory.getMemoryFormFactor().split("-")[0];

        List<String> supportedMemoryTypes = getSupportedMemoryTypes(cpu);

        // 비교된 메모리 유형 출력
        System.out.println("Supported Memory Types: " + supportedMemoryTypes);  // 지원되는 메모리 유형 출력

        return supportedMemoryTypes.contains(memoryTypeFromFormFactor);
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
        if (cpuName.matches(".*-14.*")) {
            return List.of("DDR5");
        } else if (cpuName.matches(".*-13.*") || cpuName.matches(".*-12.*")) {
            return Arrays.asList("DDR4", "DDR5"); // 12, 13세대는 DDR4, DDR5 지원
        } else if (cpuName.matches(".*-8.*|.*-9.*|.*-10.*|.*-11.*")) {
            return List.of("DDR4"); // 8~11세대는 DDR4 지원
        } else if (cpuName.matches(".*-6.*|.*-7.*")) {
            return Arrays.asList("DDR3", "DDR4"); // 6~7세대는 DDR3, DDR4 지원
        }
        return Collections.emptyList();
    }

    // AMD CPU의 지원 메모리 유형
    private List<String> getAmdSupportedMemoryTypes(String cpuName) {
        // Ryzen 1000~5000 시리즈는 DDR4 지원
        if (cpuName.matches(".*Ryzen.*[1-5][0-9]{3}X?")) {
            return List.of("DDR4");
        }
        // Ryzen 7000 시리즈는 DDR5 지원
        else if (cpuName.matches(".*Ryzen.*7[0-9]{3}X?")) {
            return List.of("DDR5");
        }

        // Threadripper 시리즈 전부 DDR4
        if (cpuName.matches(".*Threadripper.*")) {
            return List.of("DDR4");
        }

        // EPYC 시리즈 1~3세대 DDR4, 4세대 DDR5
        if (cpuName.matches(".*EPYC.*1.*|.*EPYC.*2.*|.*EPYC.*3.*")) {
            return List.of("DDR4");
        } else if (cpuName.matches(".*EPYC.*4.*")) {
            return List.of("DDR5");
        }

        return Collections.emptyList();
    }



    //Motherboard == Cover
    public boolean checkMotherboardCoverCompatibility(CartEntity cart) {
        MotherboardEntity motherboard = cart.getMotherboard();
        CoverEntity cover = cart.getCover();

        if (motherboard == null || cover == null) {
            throw new IllegalArgumentException("Motherboard or Cover is missing in the cart.");
        }

        System.out.println("Motherboard Form Factor: " + motherboard.getMotherboardFormFactor());
        System.out.println("Cover Form Factor: " + cover.getCoverMotherboardFormFactor());

        // form factor 값을 비교
        int motherboardValue = getFormFactorValue(motherboard.getMotherboardFormFactor());
        int coverValue = getFormFactorValue(cover.getCoverMotherboardFormFactor());

        // 값 확인을 위한 로그 출력
        System.out.println("Motherboard Form Factor(Value: " + motherboardValue + ")");
        System.out.println("Cover Form Factor(Value: " + coverValue + ")");


        return motherboardValue <= coverValue;
    }

    private int getFormFactorValue(String formFactor) {
        if (formFactor.contains("Mini-ITX")) {
            return 1;
        } else if (formFactor.contains("Micro-ATX")) {
            return 2;
        } else if (formFactor.contains("ATX")) {
            return 3;
        } else if (formFactor.contains("E-ATX")) {
            return 4;
        } else {
            return 0; // 기본 값
        }
    }

    //Videocard == Cover
    public boolean checkVideocardCoverCompatibility(CartEntity cart) {
        VideoCardEntity videoCard = cart.getVideocard();
        CoverEntity cover = cart.getCover();

        if (videoCard == null || cover == null) {
            throw new IllegalArgumentException("VideoCard or Cover is missing in the cart.");
        }

        Double videoCardLength = videoCard.getVideoCardLength();
        Integer coverMaxVideoCardLength = cover.getCoverMaxVideoCardLength();

        if (coverMaxVideoCardLength != null) {
            System.out.println("videoCard length: " + videoCard.getVideoCardLength());
            System.out.println("Cover length: " + cover.getCoverMaxVideoCardLength());
            // Integer 값을 Double로 변환하여 비교
            return videoCardLength <= coverMaxVideoCardLength.doubleValue();
        }
        return false; // 커버의 최대 비디오 카드 길이가 null인 경우 비교하지 않음
    }

    // PowerSupply == Cover
    public boolean checkPowerSupplyCoverCompatibility(CartEntity cart) {
        PowerSupplyEntity powerSupply = cart.getPowersupply();
        CoverEntity cover = cart.getCover();

        if (powerSupply == null || cover == null) {
            throw new IllegalArgumentException("PowerSupply or Cover is missing in the cart.");
        }

        System.out.println("PowerSupply Type: " + powerSupply.getPowerSupplyType());
        System.out.println("Cover PowerSupply: " + cover.getCoverPowerSupply());

        // PowerSupply Type과 Cover PowerSupply를 ','로 분리하여 각각의 form factor로 변환
        String[] powerSupplyTypes = powerSupply.getPowerSupplyType().split(",\\s*");  // ','로 분리하고, 공백도 제거
        String coverPowerSupply = cover.getCoverPowerSupply();

        // 값 확인을 위한 로그 출력
        System.out.println("PowerSupply Types: " + String.join(", ", powerSupplyTypes));
        System.out.println("Cover PowerSupply: " + coverPowerSupply);

        // PowerSupply 타입들 중 하나라도 Cover PowerSupply와 일치하면 true
        for (String powerSupplyType : powerSupplyTypes) {
            if (powerSupplyType.equalsIgnoreCase(coverPowerSupply)) {
                return true;
            }
        }

        // 일치하는 값이 없으면 false
        return false;
    }

    @Transactional
    public void updateCartFromGuideline(int guidelineId, String userId) {
        // 1. GuidelineEntity 가져오기
        GuidelineEntity guidelineEntity = guidelineRepository.findById(guidelineId)
                .orElseThrow(() -> new EntityNotFoundException("Guideline not found with ID: " + guidelineId));

        // 2. CartEntity 가져오기
        CartEntity cartEntity = cartRepository.findByUser_MemberId(userId);
        if (cartEntity == null) {
            throw new EntityNotFoundException("Cart not found for user ID: " + userId);
        }

        // 3. Guideline 정보를 기반으로 Cart 부품 업데이트
        cartEntity.setCpu(CpuEntity.builder().id(guidelineEntity.getCpuId()).build());
        cartEntity.setCpucooler(CpuCoolerEntity.builder().id(guidelineEntity.getCpuCoolerId()).build());
        cartEntity.setMotherboard(MotherboardEntity.builder().id(guidelineEntity.getMotherboardId()).build());
        cartEntity.setMemory(MemoryEntity.builder().id(guidelineEntity.getMemoryId()).build());
        cartEntity.setStorage(StorageEntity.builder().id(guidelineEntity.getStorageId()).build());
        cartEntity.setVideocard(VideoCardEntity.builder().id(guidelineEntity.getVideoCardId()).build());
        cartEntity.setPowersupply(PowerSupplyEntity.builder().id(guidelineEntity.getPowerSupplyId()).build());
        cartEntity.setCover(CoverEntity.builder().id(guidelineEntity.getCoverId()).build());

        // 4. CartEntity 저장
        cartRepository.save(cartEntity);
    }

}



