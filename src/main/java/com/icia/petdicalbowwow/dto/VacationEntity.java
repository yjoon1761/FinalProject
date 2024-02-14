package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="VACATION")
@SequenceGenerator(name = "VAC_SEQ_GENERATOR", sequenceName = "VAC_SEQ", allocationSize = 1)
public class VacationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VAC_SEQ_GENERATOR")
    private int vNo;

    @Column
    private String vDate;

    @Column
    private int comNo;

    public static VacationEntity toEntity(VacationDTO vacation) {
        VacationEntity entity = new VacationEntity();

        entity.setVNo(vacation.getVNo());
        entity.setVDate(vacation.getVDate());
        entity.setComNo(vacation.getComNo());

        return entity;
    }
}
