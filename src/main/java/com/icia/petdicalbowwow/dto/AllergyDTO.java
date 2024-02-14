package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("allergy")
public class AllergyDTO {

    private int aNo;

    private String aName;

    private String aContent;

    public static AllergyDTO toDTO(AllergyEntity entity){
        AllergyDTO allergy = new AllergyDTO();

        allergy.setANo(entity.getANo());
        allergy.setAName(entity.getAName());
        allergy.setAContent(entity.getAContent());

        return allergy;
    }
}