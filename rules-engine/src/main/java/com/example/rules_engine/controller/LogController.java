package com.example.rules_engine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class LogController {

    private static final Logger log = LoggerFactory.getLogger(BusinessRuleController.class);
    @GetMapping("/generate-log")
    public String generateCustomLog(@RequestParam String message) {
        log.info("Custom log message: {}", message);
        return "Log generated!";
    }
}