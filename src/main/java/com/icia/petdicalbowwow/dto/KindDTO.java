package com.icia.petdicalbowwow.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("kind")
public class KindDTO {
    private int kNo;
    private String kName;

    public static KindDTO toDTO(KindEntity entity){
        KindDTO kind = new KindDTO();

        kind.setKNo(entity.getKNo());
        kind.setKName(entity.getKName());

        return kind;

    }
}

