package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.CommunityService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/community")
@Controller
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/list")
    public String list() {
        return "community/list";
    }

    @GetMapping("/write")
    public String write() {
        return "community/write";
    }

    @GetMapping("/read")
    public String read() {
        return "community/read";
    }



}
