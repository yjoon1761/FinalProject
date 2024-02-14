package com.icia.petdicalbowwow.service;

import com.icia.petdicalbowwow.dao.AllergyRepository;
import com.icia.petdicalbowwow.dto.AllergyDTO;
import com.icia.petdicalbowwow.dto.AllergyEntity;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AllergyService {

    private final AllergyRepository arepo;


    public List<AllergyDTO> aList() {
        List<AllergyDTO> list = new ArrayList<>();

        List<AllergyEntity> entityList = arepo.findAll();

        for(AllergyEntity entity : entityList){
            list.add(AllergyDTO.toDTO(entity));
        }
        return list;
    }
}