package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="KIND")
@SequenceGenerator(name = "KOM_SEQ_GENERATOR", sequenceName = "KOM_SEQ", allocationSize = 1)
public class KindEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KOM_SEQ_GENERATOR")
    private int kNo;

    @Column
    private String kName;

    public static KindEntity toEntity(KindDTO kind){
        KindEntity entity = new KindEntity();

        entity.setKNo(kind.getKNo());
        entity.setKName(kind.getKName());

        return entity;
    }

}

