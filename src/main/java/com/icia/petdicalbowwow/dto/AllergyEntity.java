package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="ALLERGY")
@SequenceGenerator(name="ALL_SEQ_GENERATOR" , sequenceName = "ALL_SEQ" , allocationSize = 1)
public class AllergyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALL_SEQ_GENERATOR")
    private int aNo;

    @Column
    private String aName;

    @Column
    private String aContent;

    public static AllergyEntity toEntitiy(AllergyDTO allergy){
        AllergyEntity entity = new AllergyEntity();

        entity.setANo(allergy.getANo());
        entity.setAName(allergy.getAName());
        entity.setAContent(allergy.getAContent());

        return entity;
    }
}