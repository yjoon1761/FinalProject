package com.icia.petdicalbowwow.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("resModels")
public class ReservationModel {
    private String resDate;
    private int comNo;
}
