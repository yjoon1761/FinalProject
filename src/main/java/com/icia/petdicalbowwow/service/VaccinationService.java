package com.icia.petdicalbowwow.service;


import com.icia.petdicalbowwow.dao.VaccinationRepository;
import com.icia.petdicalbowwow.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VaccinationService {

    private final VaccinationRepository varepo;

    private ModelAndView mav;

    public List<VaccinationDTO> vList() {
        System.out.println("[1] html -> controller");
        List<VaccinationDTO> list = new ArrayList<>();

        List<VaccinationEntity> entityList = varepo.findAll();
        System.out.println("[2] service -> controller" + entityList);

        for(VaccinationEntity entity : entityList){
            list.add(VaccinationDTO.toDTO(entity));

        }
        System.out.println("[3] service -> controller" + list);

        return list;

    }
}