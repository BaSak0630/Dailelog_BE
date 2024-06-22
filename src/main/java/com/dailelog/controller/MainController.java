package com.dailelog.controller;

import com.dailelog.config.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String index() {
        return "메인 페이지입니다.";
    }
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return "사용자페이지입니다.";
    }

    @GetMapping("/admin")
    public String admin() {
        return "관리자페이지입니다.";
    }
}
