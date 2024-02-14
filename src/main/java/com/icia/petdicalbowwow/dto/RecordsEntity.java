package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="RECORDS")
@SequenceGenerator(name="REC_SEQ_GENERATOR",sequenceName = "REC_SEQ", allocationSize = 1)
public class RecordsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REC_SEQ_GENERATOR")
    private int rNo;
    @Column
    private String rName; //담당의
    @Column
    private String rCategory; //진료 항목
    @Column
    private String rDate; //진료 날짜
    @Column(length = 4000)
    private String rContent; //진료 내용
    @Column
    private String rTemperature; //체온

    @ManyToOne
    @JoinColumn(name = "pNo")
    private PetEntity pet;

    @ManyToOne
    @JoinColumn(name = "comNo")
    private CompanyEntity com;


    public static RecordsEntity toEntity(RecordsDTO records,PetDTO pet,CompanyDTO company){
        RecordsEntity entity = new RecordsEntity();

        entity.setRNo(records.getRNo());
        entity.setRName(records.getRName());
        entity.setRCategory(records.getRCategory());
        entity.setRDate(records.getRDate());
        entity.setRContent(records.getRContent());
        entity.setRTemperature(records.getRTemperature());
        entity.setPet(PetEntity.toEntity(pet));
        entity.setCom(CompanyEntity.toEntity(company));

        return entity;
    }
}