package com.icia.petdicalbowwow.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("member")
public class MemberDTO {
    private int MNO;             // 회원번호(sequence)
    private String MID;			// 아이디
    private String mPw;			// 비밀번호
    private String mOauth;      // api 로그인 방식
    private String mName;		// 이름
    private String nickName;   // 닉네임
    private String mBirth;		// 생년월일
    private String mGender;		// 성별
    private String mEmail;		// 이메일
    private String mPhone;		// 연락처
    private String mAddr;		// 주소

    private MultipartFile mFile;		// 업로드 파일
    private String mFileName;		// 업로드 파일이름

    public static MemberDTO toDTO(MemberEntity entity){
        MemberDTO member = new MemberDTO();

        member.setMNO(entity.getMNO());
        member.setMID(entity.getMID());
        member.setMPw(entity.getMPw());
        member.setMOauth(entity.getMOauth());
        member.setMName(entity.getMName());
        member.setNickName(entity.getNickName());
        member.setMBirth(entity.getMBirth());
        member.setMGender(entity.getMGender());
        member.setMEmail(entity.getMEmail());
        member.setMPhone(entity.getMPhone());
        member.setMAddr(entity.getMAddr());
        member.setMFileName(entity.getMFileName());

        return member;
    }

}
