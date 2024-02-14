package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("pet")
public class PetDTO {
    private int pNo;             // 강아지 번호
    private String pName;        // 이름
    private String pBirth;       // 생년월일
    private String pGender;      // 성별
    private String pKind;
    private double pHeight;      // 키
    private double pWeight;      // 몸무게
    private String pBloodType;   // 혈액형
    private String pNeutering;   // 중성화여부
    private String pAllergy;        //알러지
    private String pVaccination;    //백신접종정보
    private String pDisease;      //질병 및 장애
    private String pContent; // 특이사항
    private MemberDTO member;

    private MultipartFile pFile;  // 프로필사진
    private String pFileName;     // 파일이름

    public static PetDTO toDTO(PetEntity entity){
        PetDTO pet = new PetDTO();

        pet.setPNo(entity.getPNo());
        pet.setPName(entity.getPName());
        pet.setPBirth(entity.getPBirth());
        pet.setPGender(entity.getPGender());
        pet.setPHeight(entity.getPHeight());
        pet.setPWeight(entity.getPWeight());
        pet.setPBloodType(entity.getPBloodType());
        pet.setPNeutering(entity.getPNeutering());
        pet.setPAllergy(entity.getPAllergy());
        pet.setPVaccination(entity.getPVaccination());
        pet.setPDisease(entity.getPDisease());
        pet.setPContent(entity.getPContent());
        pet.setPFileName(entity.getPFileName());
        pet.setPKind(entity.getPKind());
        pet.setMember(MemberDTO.toDTO(entity.getMem()));

        return pet;
    }
}