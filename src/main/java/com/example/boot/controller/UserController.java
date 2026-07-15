package com.example.boot.controller;

import com.example.boot.dto.UserDTO;
import com.example.boot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user/*")
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/join")
    public void join(){}

    @PostMapping("/join")
    public String join(UserDTO userDTO){
        String email = userService.insert(userDTO);
        log.info("회원가입 완료");
        return "index";
    }

    @GetMapping("/login")
    public void login(){}
}