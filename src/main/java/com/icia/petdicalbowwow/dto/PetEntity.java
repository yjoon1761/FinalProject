package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Entity
@Table(name="PET")
@SequenceGenerator(name = "PET_SEQ_GENERATOR", sequenceName = "PET_SEQ", allocationSize = 1)
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PET_SEQ_GENERATOR")
    private int pNo;             // 강아지 번호

    @Column
    private String pName;        // 이름

    @Column
    private String pBirth;       // 생년월일

    @Column
    private String pGender;      // 성별

    @Column
    private String pKind;


    @Column
    private double pHeight;      // 키

    @Column
    private double pWeight;      // 몸무게

    @Column
    private String pBloodType;   // 혈액형

    @Column
    private String pNeutering;   // 중성화여부

    @Column
    private String pAllergy;       //알러지

    @Column
    private String pVaccination;    //백신접종정보

    @Column
    private String pDisease;      //질병 및 장애

    @Column(length = 4000)
    private String pContent; // 특이사항

    @Column
    private String pFileName;     // 파일이름

    @ManyToOne
    @JoinColumn(name = "MNO")
    private MemberEntity mem;// 회원번호

    @OneToMany(mappedBy = "pet")
    private List<RecordsEntity> recordsList;

    @OneToMany(mappedBy = "pet")
    private List<CardEntity> cardList;

    @OneToMany(mappedBy = "pet")
    private List<ReservationEntity> reservationList;

    // orphanRemoval = true
    // 품종번호

    public static PetEntity toEntity(PetDTO pet, MemberDTO member) {
        PetEntity entity = new PetEntity();

        entity.setPNo(pet.getPNo());
        entity.setPName(pet.getPName());
        entity.setPBirth(pet.getPBirth());
        entity.setPGender(pet.getPGender());
        entity.setPKind(pet.getPKind());
        entity.setPHeight(pet.getPHeight());
        entity.setPWeight(pet.getPWeight());
        entity.setPBloodType(pet.getPBloodType());
        entity.setPNeutering(pet.getPNeutering());
        entity.setPAllergy(pet.getPAllergy());
        entity.setPVaccination(pet.getPVaccination());
        entity.setPDisease(pet.getPDisease());
        entity.setPContent(pet.getPContent());
        entity.setPFileName(pet.getPFileName());
        entity.setMem(MemberEntity.toEntity(member));

        return entity;
    }

    public static PetEntity toEntity(PetDTO pet){
        PetEntity entity = new PetEntity();

        entity.setPNo(pet.getPNo());
        entity.setPName(pet.getPName());
        entity.setPBirth(pet.getPBirth());
        entity.setPGender(pet.getPGender());
        entity.setPKind(pet.getPKind());
        entity.setPHeight(pet.getPHeight());
        entity.setPWeight(pet.getPWeight());
        entity.setPBloodType(pet.getPBloodType());
        entity.setPNeutering(pet.getPNeutering());
        entity.setPAllergy(pet.getPAllergy());
        entity.setPVaccination(pet.getPVaccination());
        entity.setPDisease(pet.getPDisease());
        entity.setPContent(pet.getPContent());
        entity.setPFileName(pet.getPFileName());

        return entity;
    }
}