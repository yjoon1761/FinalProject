package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Data
@Entity
@Table(name="CARD")
@SequenceGenerator(name = "CRD_SEQ_GENERATOR", sequenceName = "CRD_SEQ", allocationSize = 1)
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRD_SEQ_GENERATOR")
    private int crdNo;        // 카드 번호

    @Column
    private String crdId;     // 카드 Id

    @Column
    private String crdNum;

    @Column
    private String crdAddr;

    @Column
    private String crdName;

    @Column
    private String crdPhone;

    @Column
    private int crdCheck;

    @ManyToOne
    @JoinColumn(name = "pNo")
    private PetEntity pet;

    public static CardEntity toEntity(CardDTO card, PetDTO pet) {
        CardEntity entity = new CardEntity();

        entity.setCrdNo(card.getCrdNo());
        entity.setCrdId(card.getCrdId());
        entity.setCrdNum(card.getCrdNum());
        entity.setCrdAddr(card.getCrdAddr());
        entity.setCrdName(card.getCrdName());
        entity.setCrdPhone(card.getCrdPhone());
        entity.setCrdCheck(card.getCrdCheck());
        entity.setPet(PetEntity.toEntity(pet));

        return entity;
    }



}