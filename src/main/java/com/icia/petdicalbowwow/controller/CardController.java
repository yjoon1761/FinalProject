package com.icia.petdicalbowwow.controller;

import com.icia.petdicalbowwow.dto.CardDTO;
import com.icia.petdicalbowwow.service.CardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CardController {

    private ModelAndView mav;
    private final CardService crdsvc;
    private String keyValue;
    private final HttpSession session;

    // 기업 페이지 이동
    @GetMapping("/cIndexForm")
    public String cIndexForm() {
        this.keyValue = null;
        return "company/cindex";
    }

    @GetMapping("/crdInfoForm")
    public String crdInfoForm(){
        session.setAttribute("No", -2);

        return "card/info";
    }

    @GetMapping("/crdListForm")
    public String crdListForm(){

        return "card/list";
    }

    @GetMapping("/crdRegiForm")
    public String crdRegiForm(){

        return "card/register";
    }

    @GetMapping("/crdUpdateForm")
    public String crdUpdateForm(){
        this.keyValue = null;
        return "card/update";
    }

    @PostMapping ("nfc")
    @ResponseBody
    public String nfc(@RequestBody Map<String, List<String>> cardInfo) {

        List<String> keyArr = new ArrayList<>();
        keyArr = cardInfo.get("key");
        System.out.println(keyArr);
        String keyValue = "";

        for(int i = 0; i < keyArr.size(); i++) {
            keyValue += keyArr.get(i);
        }
        this.keyValue = keyValue;
        System.out.println(keyValue);

        return null;
    }

    @PostMapping("keyCheck")
    @ResponseBody
    public String keyCheck() {
        String keyValue = "";
        if(this.keyValue != null) {
            keyValue = this.keyValue;
        }
        return keyValue;
    }

    @GetMapping("/crdFindForm")
    public String crdFindForm(){
        this.keyValue = null;
        return "card/find";
    }

    @PostMapping("/userSelect")
    @ResponseBody
    public CardDTO userSelect(@RequestParam("keyVal") String keyVal){
        CardDTO list = crdsvc.userSelect(keyVal);
        this.keyValue = null;
        return list;
    }

    @GetMapping("/mCard")
    public String crdList(){

        return "meminfo/mcard";
    }
}
