package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="RESERVATION")
@SequenceGenerator(name = "RES_SEQ_GENERATOR", sequenceName = "RES_SEQ", allocationSize = 1)
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RES_SEQ_GENERATOR")
    private int resNo;

    @Column
    private String resDate;

    @Column
    private String resTime;

    @Column
    private String resPurpose;

    @Column(length = 4000)
    private String resContent;

    @ManyToOne
    @JoinColumn(name = "pNo")
    private PetEntity pet;

    @ManyToOne
    @JoinColumn(name = "comNo")
    private CompanyEntity com;

    public static ReservationEntity toEntity(ReservationDTO reservation,PetDTO pet,CompanyDTO company) {
        ReservationEntity entity = new ReservationEntity();

        entity.setResNo(reservation.getResNo());
        entity.setResDate(reservation.getResDate());
        entity.setResTime(reservation.getResTime());
        entity.setResPurpose(reservation.getResPurpose());
        entity.setResContent(reservation.getResContent());
        entity.setPet(PetEntity.toEntity(pet));
        entity.setCom(CompanyEntity.toEntity(company));

        return entity;
    }
}