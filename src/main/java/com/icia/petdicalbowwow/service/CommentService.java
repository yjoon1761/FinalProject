package com.icia.petdicalbowwow.service;

import com.icia.petdicalbowwow.dao.CommentDAO;
import com.icia.petdicalbowwow.dao.CommentRepository;
import com.icia.petdicalbowwow.dto.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository crepo;

    private final CommentDAO cdao;

    private final HttpSession session;

    public List<CommentDTO> cmtList(int bNo) {
        List<CommentDTO> list = new ArrayList<>();

        List<CommentEntity> entityList = crepo.findByBoa_bNo(bNo);

        for (CommentEntity entity : entityList){
            list.add(CommentDTO.toDTO(entity));
        }

        return list;
    }

    public List<CommentDTO> cmtRegister(CommentDTO comment, BoardDTO board, MemberDTO member, CompanyDTO company, CategoryDTO category) {
        int MNO = (int)session.getAttribute("loginNo");
        member.setMNO(MNO);
        int comNo = (int)session.getAttribute("comNo");
        company.setComNo(comNo);
        System.out.println(board);
        CommentEntity cEntity = CommentEntity.toEntity(comment, board, member, company, category);
        crepo.save(cEntity);

        List<CommentDTO> list = cmtList(board.getBNo());

        return list;
    }

    public List<CommentDTO> cmtModify(CommentDTO comment, BoardDTO board, MemberDTO member, CompanyDTO company, CategoryDTO category) {
        int MNO = (int)session.getAttribute("loginNo");
        member.setMNO(MNO);
        int comNo = (int)session.getAttribute("comNo");
        company.setComNo(comNo);
        CommentEntity cEntity = CommentEntity.toEntity(comment, board, member, company, category);
        crepo.save(cEntity);

        // cList() 메소드의 return값을 list에 담는다.
        List<CommentDTO> list = cmtList(board.getBNo());
        return list;
    }

    public List<CommentDTO> cmtDelete(CommentDTO comment, BoardDTO board, MemberDTO member, CompanyDTO company, CategoryDTO category) {
        CommentEntity cEntity = CommentEntity.toEntity(comment, board, member, company, category);
        crepo.delete(cEntity);

        List<CommentDTO> list = cmtList(board.getBNo());

        return list;
    }
}
