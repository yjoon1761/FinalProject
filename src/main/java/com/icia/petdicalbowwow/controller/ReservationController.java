package com.icia.petdicalbowwow.controller;

import com.icia.petdicalbowwow.dto.CompanyDTO;
import com.icia.petdicalbowwow.dto.PetDTO;
import com.icia.petdicalbowwow.dto.ReservationDTO;
import com.icia.petdicalbowwow.dto.ReservationEntity;
import com.icia.petdicalbowwow.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService rsvc;
    private ModelAndView mav;

    @GetMapping("/rResForm")
    public String rResForm(){
        return "reservation/map";
    }

    @GetMapping("/mRes/{MNO}")
    public String mRes(Model model, @PathVariable int MNO){
        System.out.println(MNO);
        model.addAttribute("MNO", MNO);
        return  "meminfo/mres";
    }

    @PostMapping("rRegiForm")
    public ModelAndView rRegiForm(@RequestParam(value = "comNo", required = false)String comNoStr) {
        mav = new ModelAndView();
        int comNo = Integer.parseInt(comNoStr);
        mav.addObject("comNo", comNo);
        mav.setViewName("reservation/register");
        return mav;
    }

    @PostMapping("rRegister")
    public ModelAndView rRegister(@ModelAttribute ReservationDTO reservation,
                                  @ModelAttribute PetDTO pet,
                                  @ModelAttribute CompanyDTO company) {
        System.out.println("reservation = " + reservation);
        System.out.println("pet = " + pet);
        System.out.println("company = " + company);
        mav = rsvc.rRegister(reservation, pet, company);
        return mav;
    }

    @PostMapping("/rRegister2")
    public ModelAndView rRegister2(@ModelAttribute ReservationDTO reservation,
                                   @ModelAttribute CompanyDTO company,
                                   @ModelAttribute PetDTO pet){
        mav = rsvc.rRegister2(reservation, pet, company);

        return mav;
    }
    //예약 리스트
    @GetMapping("/resList")
    public String resList() {
        return "reservation/list";
    }

}

