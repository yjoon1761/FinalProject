package com.icia.petdicalbowwow.service;

import com.icia.petdicalbowwow.dao.CardRepository;
import com.icia.petdicalbowwow.dto.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository crdrepo;

    private final HttpSession session;

    private ModelAndView mav;

    public List<CardDTO> crdCheckList() {
        List<CardDTO> list = new ArrayList<>();

        List<CardEntity> entityList = crdrepo.findAllByCrdCheck();

        for(CardEntity entity : entityList){
            list.add(CardDTO.toDTO(entity));
        }

        return list;
    }

    public List<CardDTO> crdUpdate(CardDTO card, PetDTO pet, String keyValue) {
        List<CardDTO> list = new ArrayList<>();

        crdrepo.updateValue(card.getCrdNo(), pet.getPNo(), keyValue);

        list = crdCheckList();

        return list;
    }

    public CardDTO userSelect(String keyVal) {
        CardDTO list = new CardDTO();

        CardEntity entity = crdrepo.findByCrdId(keyVal);

        list = CardDTO.toDTO(entity);

        String date = list.getPet().getPBirth().substring(0, 4);

        Calendar cal = Calendar.getInstance();

        int yyyy = cal.get(Calendar.YEAR);

        int date1 = yyyy - Integer.parseInt(date) + 1;

        list.getPet().setPBirth(list.getPet().getPBirth() +" (" + String.valueOf(date1) + "살)");

        System.out.println(date);

        return list;
    }

    public List<CardDTO> crdList() {
        List<CardDTO> list = new ArrayList<>();

        List<CardEntity> entityList = crdrepo.findAll();

        for (CardEntity entity : entityList){
            list.add(CardDTO.toDTO(entity));
        }

        return list;
    }

    public List<CardDTO> crdDelete(CardDTO card, PetDTO pet) {
        CardEntity entity = CardEntity.toEntity(card, pet);

        crdrepo.delete(entity);

        List<CardDTO> list = crdList();

        return list;
    }
}
