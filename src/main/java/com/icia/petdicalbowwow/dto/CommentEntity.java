package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cache.annotation.CacheConfig;

import java.util.Date;

@Data
@Entity
@Table(name = "COMMENTS")
@SequenceGenerator(name = "COM2_SEQ_GENERATOR", sequenceName = "COM2_SEQ", allocationSize = 1)
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COM2_SEQ_GENERATOR")
    private  int cNo;

    @Column(length = 4000)
    private String cContent;

    @Column
    @UpdateTimestamp
    private Date cDate;

    @ManyToOne
    @JoinColumn(name = "mno")
    private MemberEntity mem;

    @ManyToOne
    @JoinColumn(name = "bNo")
    private BoardEntity boa;

    @ManyToOne
    @JoinColumn(name = "comNo")
    private CompanyEntity com;

    public static CommentEntity toEntity(CommentDTO comment, BoardDTO board, MemberDTO member, CompanyDTO company, CategoryDTO category){
        CommentEntity entity = new CommentEntity();

        entity.setCNo(comment.getCNo());
        entity.setCContent(comment.getCContent());
        entity.setCDate(comment.getCDate());

        entity.setMem(MemberEntity.toEntity(member));
        entity.setBoa(BoardEntity.toEntity(board, member, company, category));
        entity.setCom(CompanyEntity.toEntity(company));

        return entity;
    }
}