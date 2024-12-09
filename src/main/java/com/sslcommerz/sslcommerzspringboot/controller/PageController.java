package com.sslcommerz.sslcommerzspringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PageController {
    @PostMapping("/success")
    public String success() {
        return "success";
    }

    @PostMapping("/fail")
    public String fail() {
        return "fail";
    }

    @PostMapping("/cancel")
    public String cancel() {
        return "cancel";
    }
}
