package com.icia.petdicalbowwow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ScheduleController {

    @GetMapping("/sRegiForm")
    public String sRegiForm(){
        return "schedule/register";
    }
}
