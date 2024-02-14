package com.icia.petdicalbowwow.service;

import com.icia.petdicalbowwow.dao.DiseaseDAO;
import com.icia.petdicalbowwow.dao.DiseaseRepository;
import com.icia.petdicalbowwow.dto.DiseaseDTO;
import com.icia.petdicalbowwow.dto.DiseaseEntity;
import com.icia.petdicalbowwow.dto.KindDTO;
import com.icia.petdicalbowwow.dto.KindEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class DiseaseService {

    private final DiseaseRepository drepo;

    private DiseaseDAO ddao;

    public List<DiseaseDTO> dList() {
        System.out.println("[1] html -> controller");
        List<DiseaseDTO> list = new ArrayList<>();

        List<DiseaseEntity> entityList = drepo.findAll();
        System.out.println("[2] service -> controller" + entityList);

        for(DiseaseEntity entity : entityList){
            list.add(DiseaseDTO.toDTO(entity));

        }
        System.out.println("[3] service -> controller" + list);

        return list;

    }

}