package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Data
@Entity
@Table(name="MEMBER")
@SequenceGenerator(name = "MEM_SEQ_GENERATOR", sequenceName = "MEM_SEQ", allocationSize = 1)
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEM_SEQ_GENERATOR")
    private int MNO;

    @Column
    private String MID;			// 아이디

    @Column
    private String mPw;			// 비밀번호

    @Column
    private String mName;		// 이름

    @Column
    private String nickName;   // 닉네임

    @Column
    private String mOauth;      // api 로그인 방식

    @Column
    private String mBirth;		// 생년월일

    @Column
    private String mGender;		// 성별

    @Column
    private String mEmail;		// 이메일

    @Column
    private String mPhone;		// 연락처

    @Column
    private String mAddr;		// 주소

    @Column
    private String mFileName;		// 업로드 파일이름

    @OneToMany(mappedBy = "mem")
    private List<BoardEntity> boardList;

    @OneToMany(mappedBy = "mem")
    private List<CommentEntity> commentList;

    @OneToMany(mappedBy = "mem")
    private  List<PetEntity> petList;

    public static MemberEntity toEntity(MemberDTO member){
        MemberEntity entity = new MemberEntity();

        entity.setMNO(member.getMNO());
        entity.setMID(member.getMID());
        entity.setMPw(member.getMPw());
        entity.setMOauth(member.getMOauth());
        entity.setMName(member.getMName());
        entity.setNickName(member.getNickName());
        entity.setMBirth(member.getMBirth());
        entity.setMGender(member.getMGender());
        entity.setMEmail(member.getMEmail());
        entity.setMPhone(member.getMPhone());
        entity.setMAddr(member.getMAddr());
        entity.setMFileName(member.getMFileName());



        return entity;
    }


}