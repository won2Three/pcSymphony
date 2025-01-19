package com.example.demo.controller;

import com.example.demo.domain.entity.PcReviewEntity;
import com.example.demo.service.PcReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

//    @GetMapping({"","/"})
//    public String home() {
//        return "home";
//    }

    //-------------------------pcReview--------------------------
    @Autowired
    private PcReviewService pcReviewService;

    @GetMapping({"","/"})
    public String home(Model model) {
        List<PcReviewEntity> latestPcReviews = pcReviewService.getLatestPcReviews();
        model.addAttribute("latestPcReviews", latestPcReviews);
        return "home";
    }
    // -------------------------pcReview--------------------------
}