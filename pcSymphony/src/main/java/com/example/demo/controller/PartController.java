package com.example.demo.controller;

import com.example.demo.domain.dto.PartDTO;
import com.example.demo.domain.entity.part.*;
import com.example.demo.repository.part.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.service.PartService;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/part")
public class PartController {

    private final PartService partService;

    @Autowired
    private CpuRepository cpuRepository;
    @Autowired
    private CpuCoolerRepository cpuCoolerRepository;
    @Autowired
    private MemoryRepository memoryRepository;
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private MotherboardRepository motherboardRepository;
    @Autowired
    private CoverRepository coverRepository;
    @Autowired
    private VideoCardRepository videoCardRepository;
    @Autowired
    private PowerSupplyRepository powerSupplyRepository;

    @GetMapping("/cpu")
    public String getCpuList(Model model) {
        List<CpuEntity> cpuList = cpuRepository.findAll();
        model.addAttribute("tableName", "cpu");
        model.addAttribute("parts", cpuList);
        return "part/partList"; // part/partList.html 템플릿으로 CPU 목록을 반환
    }

    @GetMapping("/cpucooler")
    public String getCpuCoolerList(Model model) {
        List<CpuCoolerEntity> cpuCoolerList = cpuCoolerRepository.findAll();
        model.addAttribute("tableName", "cpucooler");
        model.addAttribute("parts", cpuCoolerList);
        return "part/partList"; // part/partList.html 템플릿으로 CPU Cooler 목록을 반환
    }

    @GetMapping("/videocard")
    public String getVideoCardList(Model model) {
        List<VideoCardEntity> videoCardList = videoCardRepository.findAll();
        model.addAttribute("tableName", "videocard");
        model.addAttribute("parts", videoCardList);
        return "part/partList"; // part/partList.html 템플릿으로 GPU 목록을 반환
    }

    @GetMapping("/memory")
    public String getMemoryList(Model model) {
        List<MemoryEntity> memoryList = memoryRepository.findAll();
        model.addAttribute("tableName", "memory");
        model.addAttribute("parts", memoryList);
        return "part/partList"; // part/partList.html 템플릿으로 Memory 목록을 반환
    }

    @GetMapping("/storage")
    public String getStorageList(Model model) {
        List<StorageEntity> storageList = storageRepository.findAll();
        model.addAttribute("tableName", "storage");
        model.addAttribute("parts", storageList);
        return "part/partList"; // part/partList.html 템플릿으로 Storage 목록을 반환
    }

    @GetMapping("/motherboard")
    public String getMotherboardList(Model model) {
        List<MotherboardEntity> motherboardList = motherboardRepository.findAll();
        model.addAttribute("tableName", "motherboard");
        model.addAttribute("parts", motherboardList);
        return "part/partList"; // part/partList.html 템플릿으로 Motherboard 목록을 반환
    }

    @GetMapping("/powersupply")
    public String getPowerSupplyList(Model model) {
        List<PowerSupplyEntity> powerSupplyList = powerSupplyRepository.findAll();
        model.addAttribute("tableName", "powersupply");
        model.addAttribute("parts", powerSupplyList);
        return "part/partList"; // part/partList.html 템플릿으로 Power Supply 목록을 반환
    }

    @GetMapping("/cover")
    public String getCoverList(Model model) {
        List<CoverEntity> coverList = coverRepository.findAll();
        model.addAttribute("tableName", "cover");
        model.addAttribute("parts", coverList);
        return "part/partList"; // part/partList.html 템플릿으로 Case 목록을 반환
    }
     // html controller html |||| url controller html
    @GetMapping("/{tableName}/{id}")
    public String getPartDetail(@PathVariable String tableName, @PathVariable int id, Model model) {
        PartDTO partDTO = partService.getPart(tableName, id);



        model.addAttribute("part", partDTO);
        model.addAttribute("tableName", tableName);
        return "part/partDetail";
    }


//        Object part = partService.findPartByTableAndId(tableName, id);
//        System.out.println("test!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!start!");
//        System.out.println(part);
//        System.out.println("test!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!end!");
//        model.addAttribute("part", part);
//        model.addAttribute("tableName", tableName);
//
//        return "part/partDetail";
//    }

}
