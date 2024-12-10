package com.sslcommerz.sslcommerzspringboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PageController {
    @Operation(summary = "Show the success page")
    @PostMapping("/success")
    public String success() {
        return "success";
    }

    @Operation(summary = "Show the fail page")
    @PostMapping("/fail")
    public String fail() {
        return "fail";
    }

    @Operation(summary = "Show the cancel page")
    @PostMapping("/cancel")
    public String cancel() {
        return "cancel";
    }
}
