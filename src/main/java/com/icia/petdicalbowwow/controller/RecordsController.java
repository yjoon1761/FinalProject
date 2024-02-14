package com.icia.petdicalbowwow.controller;


import com.icia.petdicalbowwow.dto.CompanyDTO;
import com.icia.petdicalbowwow.dto.PetDTO;
import com.icia.petdicalbowwow.dto.RecordsDTO;
import com.icia.petdicalbowwow.service.RecordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class RecordsController {

    private ModelAndView mav;

    private final RecordsService rsvc;

    @GetMapping("/recRegiForm/{dId}")
    public ModelAndView recRegiForm(@PathVariable int dId) {
        mav = rsvc.recRegiForm(dId);
        return mav;
    }

    //recRegister
    @PostMapping("/recRegister")
    public ModelAndView recRegister(@ModelAttribute RecordsDTO records,
                                    @ModelAttribute PetDTO pet,
                                    @ModelAttribute CompanyDTO company){
        System.out.println("controller"+records);
        mav=rsvc.recRegister(records,pet,company);

        return mav;

    }

    //차트 목록 페이지 이동
    @GetMapping("/recList")
    public String recList() {
        return "records/list";
    }

    @GetMapping("/recView/{rNo}")
    public ModelAndView recView(@PathVariable int rNo) {
        mav = rsvc.recView(rNo);
        return mav;
    }

    @GetMapping("/recModiForm/{rNo}")
    public ModelAndView recModiForm(@PathVariable int rNo) {
        mav = rsvc.recModiForm(rNo);
        return mav;
    }

    @PostMapping("/recModify")
    public ModelAndView recModify(@ModelAttribute RecordsDTO records,
                                  @ModelAttribute PetDTO pet,
                                  @ModelAttribute CompanyDTO company) {

        mav = rsvc.recModify(records, pet, company);

        return mav;
    }
    //차트 목록(병원별)페이지 이동
    @GetMapping("/comRecList")
    public String comRecList() {
        return "records/clist";
    }

    //차트 목록(개인별)페이지 이동
    @GetMapping("/memRecList")
    public String memRecList() {
        return "records/mlist";
    }

    @GetMapping("/mrecList")
    public String mrecList() {
        return "records/mlist";
    }


}




