package com.icia.petdicalbowwow.controller;


import com.icia.petdicalbowwow.dto.CompanyDTO;
import com.icia.petdicalbowwow.service.CompanyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class CompanyController {

    private ModelAndView mav;

    private final CompanyService csvc;

    private final HttpSession session;

    //cJoinForm
    @GetMapping("/cJoinForm")
    public String cJoinForm() {
        return "join2";
    }

    //cJoin
    @PostMapping("/cJoin")
    public ModelAndView cJoin(@ModelAttribute CompanyDTO company) {
        System.out.println("cJoin : 회원 가입 메소드");
        System.out.println("[1]jsp->controller : " + company);

        mav = csvc.cJoin(company);
        System.out.println("[4] service -> controller");

        return mav;
    }

    //cLoginForm
    @GetMapping("/cLoginForm")
    public String cLoginForm() {
        return "company/login";
    }

    //cLogin
    @PostMapping("/cLogin")
    public ModelAndView cLogin(@ModelAttribute CompanyDTO company) {
        System.out.println("cLogin : 로그인 메서드");
        System.out.println("[1]jsp-controller : " + company);

        mav = csvc.cLogin(company);

        System.out.println("mav : " + mav);

        return mav;
    }

    //cLogin
    @PostMapping("/cLogin2")
    public ModelAndView cLogin2(@ModelAttribute CompanyDTO company) {
        System.out.println("cLogin : 로그인 메서드");
        System.out.println("[1]jsp-controller : " + company);

        mav = csvc.cLogin2(company);

        System.out.println("mav : " + mav);

        return mav;
    }

    //cLogOut
    @GetMapping("/cLogout")
    public String cLogout() {
        session.invalidate();
        return "index";
    }

    //comList:회사 목록 페이지로 이동
    @GetMapping("/cListForm")
    public String comList() {

        return "company/list";
    }

    //cView 넘버로 조회
    @GetMapping("/cView/{comNo}")
    public ModelAndView cView(@PathVariable int comNo) {

        System.out.println("cView :상세보기 메소드");
        System.out.println("[1]jsp->controller : " + comNo);

        mav = csvc.cView(comNo);
        return mav;
    }

    //cModify
    @PostMapping("/cModify")
    public ModelAndView cModify(@ModelAttribute CompanyDTO company) {
        System.out.println("수정 : " + company);
        mav = csvc.cModify(company);
        return mav;
    }

    //cDelete : 회원삭제
    @GetMapping("/cDelete/{ComNo}")
    public ModelAndView cDelete(@PathVariable int ComNo) {
        mav = csvc.cDelete(ComNo);
        return mav;
    }

    @GetMapping("cIdForm")
    public String cIdForm() {
        return "company/findId";
    }

    @PostMapping("cIdFind")
    public ModelAndView cIdFind(@RequestParam(value = "findIdEmail") String findIdEmail,
                                @RequestParam(value = "findIdPhone") String findIdPhone) {
        System.out.println("findIdEmail = " + findIdEmail);
        System.out.println("findIdPhone = " + findIdPhone);
        mav = csvc.cIdFind(findIdEmail, findIdPhone);
        return mav;
    }

    @GetMapping("cPwForm")
    public String cPwForm() {
        return "company/findPw";
    }

    @PostMapping("cPwModiForm")
    public String cPwModiForm(@RequestParam(value = "findPwId") String findPwId) {
        System.out.println("findPwId = " + findPwId);
        session.setAttribute("findPwId", findPwId);
        return "company/modifyPw";
    }

    @PostMapping("cModifyPw")
    public ModelAndView cModifyPw(@RequestParam(value = "CPW") String CPW) {
        System.out.println("CPW = " + CPW);
        mav = csvc.cModifyPw(CPW);
        return mav;
    }

    //기업 페이지 차트작성 페이지 이동
//    @GetMapping("recRegiForm")
//    public String recRegiForm(){
//        return "records/register";
//    }


    //기업 페이지 차트 리스트
    @GetMapping("/crecList")
    public String crecList() {
        return "records/clist";
    }

}








