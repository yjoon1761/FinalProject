package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="VACCINATION")
@SequenceGenerator(name = "VOM_SEQ_GENERATOR", sequenceName = "VOM_SEQ", allocationSize = 1)
public class VaccinationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VOM_SEQ_GENERATOR")
    private int vNo;

    @Column
    private String vName;

    @Column
    private String vContent;

    public static VaccinationEntity toEntity(VaccinationDTO vaccination){
        VaccinationEntity entity = new VaccinationEntity();

        entity.setVNo(vaccination.getVNo());
        entity.setVName(vaccination.getVName());
        entity.setVContent(vaccination.getVContent());

        return entity;
    }
}