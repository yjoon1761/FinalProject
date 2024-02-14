package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("card")
public class CardDTO {
    private int crdNo;        // 카드 번호
    private String crdId;     // 카드 Id
    private String crdNum;
    private String crdAddr;
    private String crdName;
    private String crdPhone;
    private int crdCheck;
    private PetDTO pet;

    public static CardDTO toDTO(CardEntity entity) {
        CardDTO card = new CardDTO();

        card.setCrdNo(entity.getCrdNo());
        card.setCrdId(entity.getCrdId());
        card.setCrdNum(entity.getCrdNum());
        card.setCrdAddr(entity.getCrdAddr());
        card.setCrdName(entity.getCrdName());
        card.setCrdPhone(entity.getCrdPhone());
        card.setCrdCheck(entity.getCrdCheck());
        card.setPet(PetDTO.toDTO(entity.getPet()));

        return card;
    }
}