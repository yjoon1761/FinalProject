package com.icia.petdicalbowwow.controller;

import com.icia.petdicalbowwow.dto.CompanyDTO;
import com.icia.petdicalbowwow.dto.DoctorDTO;
import com.icia.petdicalbowwow.service.DoctorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class DoctorController {

    private ModelAndView mav;

    private final DoctorService dsvc;

    @GetMapping("/dRegiForm")
    private String dRegiForm(){
        return "doctor/register";
    }

    @PostMapping("/dRegister")
    private ModelAndView dRegister(@ModelAttribute DoctorDTO doctor,
                                   @ModelAttribute CompanyDTO company){
        mav = dsvc.dRegister(doctor, company);

        return mav;
    }

    @GetMapping("/dListForm")
    public String dListForm(){
        return "doctor/list";
    }

    @GetMapping("/dView/{dNo}")
    public ModelAndView dView(@PathVariable int dNo){
        mav = dsvc.dView(dNo);
        return mav;
    }

    @GetMapping("/dModiForm/{dNo}")
    public ModelAndView dModiForm(@PathVariable int dNo){
        mav = dsvc.dModiForm(dNo);
        return mav;
    }

    @PostMapping("/dModify")
    public ModelAndView dModify(@ModelAttribute DoctorDTO doctor,
                                @ModelAttribute CompanyDTO company){
        mav = dsvc.dModify(doctor, company);
        return mav;
    }

    @GetMapping("/dDelete/{dNo}")
    public ModelAndView dDelete(@PathVariable int dNo){
        mav = dsvc.dDelete(dNo);
        return mav;
    }
}