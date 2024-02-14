package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("doctor")
public class DoctorDTO {

    private int dNo;
    private int dId;
    private String dName;
    private String dFileName;
    private MultipartFile dFile;
    private String dContent;

    private CompanyDTO company;

    public static DoctorDTO toDTO(DoctorEntity entity){
        DoctorDTO doctor = new DoctorDTO();

        doctor.setDNo(entity.getDNo());
        doctor.setDId(entity.getDId());
        doctor.setDName(entity.getDName());
        doctor.setDFileName(entity.getDFileName());
        doctor.setDContent(entity.getDContent());
        doctor.setCompany(CompanyDTO.toDTO(entity.getCom()));

        return doctor;
    }
}
