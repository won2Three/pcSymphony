package com.example.demo.controller;

import com.example.demo.domain.dto.CommunityDto;
import com.example.demo.security.MemberUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.CommunityService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("community")
@Controller
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("list")
    public String list() {
        return "community/list";
    }

    @GetMapping("write")
    public String write() {
        return "community/write";
    }

    @PostMapping("write")
    public String write(
            CommunityDto community,
            @AuthenticationPrincipal MemberUserDetails user) {
        community.setMemberId(user.getUsername());
        communityService.write(community);
        return "redirect:list";
    }

    @GetMapping("read")
    public String read(@RequestParam("communityId") int communityId,
                       Model model) {
        CommunityDto communityDto = communityService.getCommunity(communityId);
        model.addAttribute("community", communityDto);
        return "community/read";
    }


    @GetMapping("delete")
    public String delete(@RequestParam("communityId") int communityId,
                         @AuthenticationPrincipal MemberUserDetails user) {
        communityService.delete(communityId, user.getUsername());
        return "redirect:list";
    }

    @PostMapping("update")
    @ResponseBody
    public String upadate(@RequestBody CommunityDto communityDto,
                          @AuthenticationPrincipal MemberUserDetails user) {
        communityService.update(communityDto.getCommunityId(), communityDto, user.getUsername());
        return "redirect:list";
    }
}
