package com.balan.sergii.loan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class Version {
	
    @GetMapping
    public String getVersion() {
        return "1.0.0";
    }    
}