package com.sunginfo.trade_account_manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
 
    @RequestMapping("/")
    String home() {
        return "Hello World---!";
    }
 
}