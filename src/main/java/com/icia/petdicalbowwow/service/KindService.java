package com.icia.petdicalbowwow.service;


import com.icia.petdicalbowwow.dao.KindDAO;
import com.icia.petdicalbowwow.dao.KindRepository;
import com.icia.petdicalbowwow.dto.KindDTO;
import com.icia.petdicalbowwow.dto.KindEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KindService {

    private ModelAndView mav;

    private final KindDAO kdao;

    private final KindRepository krepo;

    public List<KindDTO> kList() {
        System.out.println("[1] html -> controller");
        List<KindDTO> list = new ArrayList<>();

        List<KindEntity> entityList = krepo.findAllAcs();
        System.out.println("[2] service -> controller" + entityList);

        for(KindEntity entity : entityList){
            list.add(KindDTO.toDTO(entity));
        }
        System.out.println("[3] service -> controller" + list);

        return list;
    }

}