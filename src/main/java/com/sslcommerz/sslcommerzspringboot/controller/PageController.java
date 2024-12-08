package com.sslcommerz.sslcommerzspringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PageController {
    @GetMapping("/success")
    public String success() {
        return "success"; // Refers to success.html in the templates folder
    }

    @GetMapping("/fail")
    public String fail() {
        return "fail"; // Refers to fail.html in the templates folder
    }

    @GetMapping("/cancel")
    public String cancel() {
        return "cancel"; // Refers to cancel.html in the templates folder
    }
}
