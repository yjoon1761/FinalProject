package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "BOARD")
@SequenceGenerator(name = "BOA_SEQ_GENERATOR", sequenceName = "BOA_SEQ", allocationSize = 1)
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOA_SEQ_GENERATOR")
    private int bNo;

    @Column
    private String bTitle;

    @Column(length = 4000)
    private String bContent;

    @Column(length = 4000)
    private int bHit;

    @Column
    @UpdateTimestamp
    private Date bDate;

    @Column
    private int bLike;

    @Column
    private String bFileName;

    @ManyToOne
    @JoinColumn(name = "mno")
    private MemberEntity mem;

    @ManyToOne
    @JoinColumn(name = "comNo")
    private CompanyEntity com;

    @OneToMany(mappedBy = "boa")
    private List<CommentEntity> commentList;

    @ManyToOne
    @JoinColumn(name = "catNo")
    private CategoryEntity cat;

    public static BoardEntity toEntity(BoardDTO board, MemberDTO member, CompanyDTO company, CategoryDTO category){
        BoardEntity entity = new BoardEntity();

        entity.setBNo(board.getBNo());
        entity.setBTitle(board.getBTitle());
        entity.setBContent(board.getBContent());
        entity.setBHit(board.getBHit());
        entity.setBDate(board.getBDate());
        entity.setBLike(board.getBLike());
        entity.setBFileName(board.getBFileName());

        entity.setMem(MemberEntity.toEntity(member));
        entity.setCom(CompanyEntity.toEntity(company));
        entity.setCat(CategoryEntity.toEntity(category));

        return entity;
    }
}
