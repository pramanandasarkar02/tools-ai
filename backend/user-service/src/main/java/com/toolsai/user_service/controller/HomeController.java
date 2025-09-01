package com.toolsai.user_service.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/home")
public class HomeController {
    @GetMapping()
    public String home() {
        return "User Service";
    }

}
