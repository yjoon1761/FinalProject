package com.icia.petdicalbowwow.controller;
import com.icia.petdicalbowwow.dto.MemberDTO;
import com.icia.petdicalbowwow.dto.PetDTO;
import com.icia.petdicalbowwow.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class PetController {

    private ModelAndView mav;
    private final PetService psvc;

    //펫등록 페이지 이동
    @GetMapping("/pRegiForm")
    public String pRegiForm() {
        return "pet/register";
    }

    @PostMapping("/pRegister")
    public ModelAndView pRegister(@ModelAttribute PetDTO pet,
                                  @ModelAttribute MemberDTO member) {
        System.out.println("pet=" + pet);
        System.out.println("member=" + member);
        mav = psvc.pRegister(pet, member);

        return mav;
    }

    //펫리스트
    @GetMapping("/pListForm")
    public String pListForm() {
        System.out.println("[1] jsp → controller");
        return "meminfo/plist";
    }

    @GetMapping("/pView/{pNo}")
    public ModelAndView pView(@PathVariable int pNo) {
        mav = psvc.pView(pNo);

        return mav;
    }

    @GetMapping("/pModiForm/{pNo}")
    public ModelAndView pModiForm(@PathVariable int pNo) {
        mav = psvc.pModiForm(pNo);

        return mav;
    }

    @PostMapping("/pModify")
    public ModelAndView pModify(@ModelAttribute PetDTO pet,
                                @ModelAttribute MemberDTO member) {
        mav = psvc.pModify(pet, member);

        return mav;
    }

    @GetMapping("/pDelete/{pNo}")
    public ModelAndView pDelete(@PathVariable int pNo) {
        mav = psvc.pDelete(pNo);

        return mav;
    }
}
