package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Data
@Alias("vacation")
public class VacationDTO {
    private int vNo;
    private String vDate;
    private int comNo;

    public static VacationDTO toDTO(VacationEntity entity) {
        VacationDTO vacation = new VacationDTO();
        vacation.setVNo(entity.getVNo());
        vacation.setVDate(entity.getVDate());
        vacation.setComNo(entity.getComNo());

        return vacation;
    }
}
