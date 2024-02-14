package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="DISEASE")
@SequenceGenerator(name = "DOM_SEQ_GENERATOR", sequenceName = "DOM_SEQ", allocationSize = 1)
public class DiseaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOM_SEQ_GENERATOR")
    private int dNo;

    @Column
    private String dName;

    @Column
    private String dContent;

    public static  DiseaseEntity toEntity(DiseaseDTO disease){
        DiseaseEntity entity = new  DiseaseEntity();

        entity.setDNo(disease.getDNo());
        entity.setDName(disease.getDName());
        entity.setDContent(disease.getDContent());

        return entity;
    }

}