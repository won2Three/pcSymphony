package com.example.demo.controller;

import com.example.demo.domain.dto.CommunityDTO;
import com.example.demo.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("community")
@RequiredArgsConstructor
public class CommunityRestController {

    private final CommunityService communityService;

    @PostMapping("getList")
    public List<CommunityDTO> getList() {
        return communityService.getList();
    }



}
