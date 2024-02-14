package com.icia.petdicalbowwow.controller;

import com.icia.petdicalbowwow.dto.CardDTO;
import com.icia.petdicalbowwow.dto.MemberDTO;
import com.icia.petdicalbowwow.dto.PetDTO;
import com.icia.petdicalbowwow.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private ModelAndView mav;
    private final MemberService msvc;
    private final HttpSession session;


    @GetMapping("/mIndexForm")
    public String mIndexForm() {
        return "index";
    }

    // joinForm : 회원가입 페이지로 이동
    @GetMapping("/mJoinForm")
    public String mJoinForm() {
        return "member/join";
    }

    // mJoin : 회원가입
    @PostMapping("/mJoin")
    public ModelAndView mJoin(@ModelAttribute MemberDTO member) {
        mav = msvc.mJoin(member);
        return mav;
    }

    // mLogin : 로그인
    @PostMapping("/mLogin")
    public ModelAndView mLogin(@ModelAttribute MemberDTO member) {
        System.out.println("member = " + member);
        mav = msvc.mLogin(member);
        return mav;
    }

    // 카카오 로그인 페이지 이동
    @GetMapping("/kLoginForm")
    public String kLoginForm() {
        return "redirect:https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=beb82237075bbae4f308ebdc707b3ffc&redirect_uri=http://localhost:9091/auth/kakao/callback";
    }

    // 카카오 로그인
    @GetMapping("/auth/kakao/callback")
    public ModelAndView kLogin(@RequestParam(value = "code") String code) {
        System.out.println("Authorization code = " + code);
        mav = msvc.kLogin(code);
        return mav;
    }

    // mLogout : 로그아웃
    @GetMapping("/mLogout")
    public String mLogout() {
        session.invalidate();
        return "redirect:/";
    }

    // kLogout : 카카오 로그아웃
    @GetMapping("/kLogout")
    public ModelAndView kLogout() {
        mav = msvc.kLogout();
        return mav;
    }

    // kDiscon : 카카오 연결해제
    @GetMapping("/kDiscon")
    public ModelAndView kDiscon() {
        mav = msvc.kDiscon();
        return mav;
    }

    // mListForm : 회원목록 페이지로 이동
    @GetMapping("mListForm")
    public String mListForm() {
        return "meminfo/mlist";
    }


    // mView : 회원상세보기
    @GetMapping("/mView/{MNO}")
    public ModelAndView mView(@PathVariable int MNO) {
        mav = msvc.mView(MNO);
        return mav;
    }

    // mModify : 회원수정
    @PostMapping("/mModify")
    public ModelAndView mModify(@ModelAttribute MemberDTO member) {
        mav = msvc.mModify(member);
        return mav;
    }

    // mDelete : 회원삭제
    @GetMapping("/mDelete/{MNO}")
    public ModelAndView mDelete(@PathVariable int MNO) {
        mav = msvc.mDelete(MNO);
        System.out.println("회원정보 삭제 완료");
        return mav;
    }

    // 카카오페이 결제준비
    @GetMapping("/kPayReady")
    public ModelAndView kPayReady(@ModelAttribute CardDTO card,
                                  @ModelAttribute PetDTO pet) {
        System.out.println("card : " + card);
        System.out.println("pet : " + pet);
        mav = msvc.kPayReady(card, pet);
        return mav;
    }

    @GetMapping("/kPay")
    public ModelAndView kPay(@RequestParam(value = "pg_token") String pg_token,
                             @ModelAttribute CardDTO card,
                             @ModelAttribute PetDTO pet) {
        mav = msvc.kPay(pg_token, card, pet);
        return mav;
    }

    @GetMapping("mIdForm")
    public String mIdForm() {
        return "member/findId";
    }


    @PostMapping("mIdFind")
    public ModelAndView mIdFind(@RequestParam(value = "findIdEmail") String findIdEmail,
                                @RequestParam(value = "findIdPhone") String findIdPhone) {
        System.out.println("findIdEmail = " + findIdEmail);
        System.out.println("findIdPhone = " + findIdPhone);
        mav = msvc.mIdFind(findIdEmail, findIdPhone);
        return mav;
    }

    @GetMapping("mPwForm")
    public String mPwForm() {
        return "member/findPw";
    }


    @PostMapping("mPwModiForm")
    public String mPwModiForm(@RequestParam(value = "findPwId") String findPwId) {
        System.out.println("findPwId = " + findPwId);
        session.setAttribute("findPwId", findPwId);
        return "member/modifyPw";
    }

    @PostMapping("mModifyPw")
    public ModelAndView mModifyPw(@RequestParam(value = "MPW") String MPW) {
        System.out.println("MPW = " + MPW);
        mav = msvc.mModifyPw(MPW);
        return mav;
    }

    //고객센터 페이지 이동
    @GetMapping("/cService")
    public String cService() {
        return "service";
    }
}