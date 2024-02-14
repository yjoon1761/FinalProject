package com.icia.petdicalbowwow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @PostMapping("/toIndex")
    public String toIndex() {
        return "redirect:/";
    }
}
