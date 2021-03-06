package com.learning.api.checklistappapi.controller;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    public String helloWorld(@RequestParam(required = false) String name ){
        return "hello "+(StringUtils.hasText(name) ? name : "world") ;
    }
}
