package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("disease")
public class DiseaseDTO {

    private int dNo;

    private String dName;

    private String dContent;

    public static  DiseaseDTO toDTO(DiseaseEntity entity) {
        DiseaseDTO disease = new DiseaseDTO();

        disease.setDNo(entity.getDNo());
        disease.setDName(entity.getDName());
        disease.setDContent(entity.getDContent());

        return disease;
    }
}