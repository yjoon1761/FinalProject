package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@Alias("records")
public class RecordsDTO {
    private int rNo; //진료기록 번호
    private String rName; //담당의
    private String rCategory; //진료 항목
    private String rDate; //진료 날짜
    private String rContent; //진료 내용
    private String rTemperature; //체온
    private PetDTO pet; //펫 번호
    private CompanyDTO company; //회사 번호

    public static RecordsDTO toDTO(RecordsEntity entity){
        RecordsDTO records = new RecordsDTO();
        records.setRNo(entity.getRNo());
        records.setRName(entity.getRName());
        records.setRCategory(entity.getRCategory());
        records.setRDate(entity.getRDate());
        records.setRContent(entity.getRContent());
        records.setRTemperature(entity.getRTemperature());
        records.setPet(PetDTO.toDTO(entity.getPet()));
        records.setCompany(CompanyDTO.toDTO(entity.getCom()));

        return records;
    }
}