package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "DOCTOR")
@SequenceGenerator(name = "DOC_SEQ_GENERATOR", sequenceName = "DOC_SEQ", allocationSize = 1)
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOC_SEQ_GENERATOR")
    private int dNo;

    @Column
    private int dId;

    @Column
    private String dName;

    @Column
    private String dFileName;

    @Column(length = 4000)
    private String dContent;

    @ManyToOne
    @JoinColumn(name = "comNo")
    private CompanyEntity com;

    public static DoctorEntity toEntity(DoctorDTO doctor, CompanyDTO company){
        DoctorEntity entity = new DoctorEntity();

        entity.setDNo(doctor.getDNo());
        entity.setDId(doctor.getDId());
        entity.setDName(doctor.getDName());
        entity.setDFileName(doctor.getDFileName());
        entity.setDContent(doctor.getDContent());
        entity.setCom(CompanyEntity.toEntity(company));

        return entity;
    }
}
