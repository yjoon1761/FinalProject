package com.icia.petdicalbowwow.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Data
@Table(name = "CATEGORY")
public class CategoryEntity {
    @Id
    @OnDelete(action= OnDeleteAction.CASCADE)
    private int catNo;

    @Column
    private String catName;

    @OneToMany(mappedBy = "cat")
    private List<BoardEntity> boardList;

    public static CategoryEntity toEntity(CategoryDTO category){
        CategoryEntity entity = new CategoryEntity();

        entity.setCatNo(category.getCatNo());
        entity.setCatName(category.getCatName());

        return entity;
    }
}