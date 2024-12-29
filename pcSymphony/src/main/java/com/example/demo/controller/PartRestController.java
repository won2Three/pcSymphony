package com.example.demo.controller;

import com.example.demo.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("part")
@RequiredArgsConstructor
public class PartRestController {

    private final PartService partService;


}
