package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Alias("board")
public class BoardDTO {
    private int bNo;
    private String bTitle;
    private String bContent;
    private int bHit;
    private Date bDate;
    private int bLike;

    private MultipartFile bFile;
    private String bFileName;

    private MemberDTO member;
    private CompanyDTO company;
    private CategoryDTO category;

    public static BoardDTO toDTO(BoardEntity entity){
        BoardDTO board = new BoardDTO();

        board.setBNo(entity.getBNo());
        board.setBTitle(entity.getBTitle());
        board.setBContent(entity.getBContent());
        board.setBHit(entity.getBHit());
        board.setBDate(entity.getBDate());
        board.setBLike(entity.getBLike());
        board.setBFileName(entity.getBFileName());

        board.setMember(MemberDTO.toDTO(entity.getMem()));
        board.setCompany(CompanyDTO.toDTO(entity.getCom()));
        board.setCategory(CategoryDTO.toDTO(entity.getCat()));

        return board;
    }
}
