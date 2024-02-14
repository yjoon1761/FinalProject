package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("reservation")
public class ReservationDTO {
    private int resNo;
    private String resDate;
    private String resTime;
    private String resPurpose;
    private String resContent;
    private PetDTO pet; //펫 번호
    private CompanyDTO company; //회사 번호

    public static ReservationDTO toDTO(ReservationEntity entity) {
        ReservationDTO reservation = new ReservationDTO();
        reservation.setResNo(entity.getResNo());
        reservation.setResDate(entity.getResDate());
        reservation.setResTime(entity.getResTime());
        reservation.setResPurpose(entity.getResPurpose());
        reservation.setResContent(entity.getResContent());
        reservation.setPet(PetDTO.toDTO(entity.getPet()));
        reservation.setCompany(CompanyDTO.toDTO(entity.getCom()));

        return reservation;
    }
}