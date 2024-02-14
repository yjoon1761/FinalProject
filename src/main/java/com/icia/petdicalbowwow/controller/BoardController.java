package com.icia.petdicalbowwow.controller;

import com.icia.petdicalbowwow.dto.BoardDTO;
import com.icia.petdicalbowwow.dto.CategoryDTO;
import com.icia.petdicalbowwow.dto.CompanyDTO;
import com.icia.petdicalbowwow.dto.MemberDTO;
import com.icia.petdicalbowwow.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private ModelAndView mav;

    private final BoardService bsvc;

    @GetMapping("bRegiForm")
    public String bRegiForm(){
        return "board/register";
    }

    @PostMapping("bRegister")
    public ModelAndView bRegister(@ModelAttribute BoardDTO board,
                                  @ModelAttribute MemberDTO member,
                                  @ModelAttribute CompanyDTO company,
                                  @ModelAttribute CategoryDTO category){
        System.out.println("board : " + board);
        System.out.println("member : " + member);
        System.out.println("company : " + company);
        mav = bsvc.bRegister(board, member, company, category);
        System.out.println(mav);

        return mav;
    }

    @GetMapping("/bList/{catNo}")
    public String bList(Model model, @PathVariable int catNo){
        System.out.println(catNo);
        model.addAttribute("catNo", catNo);
        return "board/list";
    }

    @GetMapping("/mBoard/{loginNo}")
    public String mBoard(Model model, @PathVariable int loginNo){
        System.out.println(loginNo);
        model.addAttribute("loginNo", loginNo);
        return "meminfo/mBoard";
    }

    @GetMapping("/bView/{bNo}")
    public ModelAndView bView(@PathVariable int bNo){
        mav = bsvc.bView(bNo);

        return mav;
    }

    @GetMapping("/gView/{bNo}")
    public ModelAndView gView(@PathVariable int bNo){
        mav = bsvc.gView(bNo);

        return mav;
    }

    @GetMapping("/bModiForm/{bNo}")
    public ModelAndView bModiForm(@PathVariable int bNo){
        mav = bsvc.bModiForm(bNo);

        return mav;
    }

    @PostMapping("/bModify")
    public ModelAndView bModify(@ModelAttribute BoardDTO board,
                                @ModelAttribute MemberDTO member,
                                @ModelAttribute CompanyDTO company,
                                @ModelAttribute CategoryDTO category){
        System.out.println("board : " + board);
        System.out.println("member : " + member);
        System.out.println("company : " + company);
        mav = bsvc.bModify(board, member, company, category);

        return mav;
    }

    @GetMapping("/bDelete/{bNo}")
    public ModelAndView bDelete(@PathVariable int bNo){
        mav = bsvc.bDelete(bNo);

        return mav;
    }

    @GetMapping("/gModiForm/{bNo}")
    public ModelAndView gModiForm(@PathVariable int bNo){
        mav = bsvc.gModiForm(bNo);

        return mav;
    }

    @PostMapping("/gModify")
    public ModelAndView gModify(@ModelAttribute BoardDTO board,
                                @ModelAttribute MemberDTO member,
                                @ModelAttribute CompanyDTO company,
                                @ModelAttribute CategoryDTO category){
        System.out.println("board : " + board);
        System.out.println("member : " + member);
        System.out.println("company : " + company);
        mav = bsvc.gModify(board, member, company, category);

        return mav;
    }

    @GetMapping("/gDelete/{bNo}")
    public ModelAndView gDelete(@PathVariable int bNo){
        mav = bsvc.gDelete(bNo);

        return mav;
    }
}
