package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@Alias("comment")
public class CommentDTO {
    private int cNo;
    private String cContent;
    private Date cDate;

    private MemberDTO member;
    private BoardDTO board;
    private CompanyDTO company;

    public static CommentDTO toDTO(CommentEntity entity){
        CommentDTO comment = new CommentDTO();

        comment.setCNo(entity.getCNo());
        comment.setCContent(entity.getCContent());
        comment.setCDate(entity.getCDate());

        comment.setMember(MemberDTO.toDTO(entity.getMem()));
        comment.setBoard(BoardDTO.toDTO(entity.getBoa()));
        comment.setCompany(CompanyDTO.toDTO(entity.getCom()));

        return comment;
    }
}
