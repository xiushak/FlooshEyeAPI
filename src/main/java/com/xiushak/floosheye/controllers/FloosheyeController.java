package com.xiushak.floosheye.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FloosheyeController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
