package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("vaccination")
public class VaccinationDTO {
    private int vNo;

    private String vName;

    private String vContent;

    public static VaccinationDTO toDTO(VaccinationEntity entity) {
        VaccinationDTO vaccination = new VaccinationDTO();

        vaccination.setVNo(entity.getVNo());
        vaccination.setVName(entity.getVName());
        vaccination.setVContent(entity.getVContent());

        return vaccination;
    }

}