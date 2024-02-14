package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@Entity
@Table(name="COMPANY")
@SequenceGenerator(name = "COM1_SEQ_GENERATOR", sequenceName = "COM1_SEQ", allocationSize = 1)
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COM1_SEQ_GENERATOR")
    @OnDelete(action= OnDeleteAction.CASCADE)
    private int comNo;            // 회사번호

    @Column
    private int cbsNum;         // 사업자번호

    @Column
    private String cId;			// 아이디

    @Column
    private String cPw;			// 비밀번호

    @Column
    private String cName;		// 이름

    @Column
    private String cEmail;		// 이메일

    @Column
    private String cTel;		// 연락처

    @Column
    private String cAddr;		// 주소

    @Column
    private String cFileName;      //파일등록


    @OneToMany(mappedBy = "com", cascade=CascadeType.REMOVE)
    private List<BoardEntity> boardList;

    @OneToMany(mappedBy = "com")
    private List<CommentEntity> commentList;

    @OneToMany(mappedBy = "com")
    private List<DoctorEntity> doctorList;

    @OneToMany(mappedBy = "com")
    private List<RecordsEntity> recordsList;

    public static CompanyEntity toEntity(CompanyDTO company){
        CompanyEntity entity = new CompanyEntity();

        entity.setComNo(company.getComNo());
        entity.setCbsNum(company.getCbsNum());
        entity.setCId(company.getCId());
        entity.setCPw(company.getCPw());
        entity.setCName(company.getCName());
        entity.setCEmail(company.getCEmail());
        entity.setCTel(company.getCTel());
        entity.setCAddr(company.getCAddr());
        entity.setCFileName(company.getCFileName());

        return entity;
    }


}