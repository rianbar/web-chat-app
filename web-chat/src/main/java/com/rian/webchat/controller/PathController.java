package com.rian.webchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PathController {

    @GetMapping("/home")
    public String homePath() {
        return "home.html";
    }

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/rooms")
    public String chatHomePath() {
        return "chatPage.html";
    }
}
