package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("company")
public class CompanyDTO {
    private int comNo;             // 회사번호(sequence)
    private int cbsNum;          // 사업자번호
    private String cId;          // 아이디
    private String cPw;          // 비밀번호
    private String cName;        // 회사명
    private String cEmail;       // 이메일
    private String cTel;         // 전화번호
    private String cAddr;        // 주소

    private MultipartFile cFile;  // 프로필사진
    private String cFileName;     // 파일이름

    public static CompanyDTO toDTO(CompanyEntity entity) {
        CompanyDTO company = new CompanyDTO();

        company.setComNo(entity.getComNo());
        company.setCbsNum(entity.getCbsNum());
        company.setCId(entity.getCId());
        company.setCPw(entity.getCPw());
        company.setCName(entity.getCName());
        company.setCEmail(entity.getCEmail());
        company.setCTel(entity.getCTel());
        company.setCAddr(entity.getCAddr());
        company.setCFileName(entity.getCFileName());

        return company;
    }
}