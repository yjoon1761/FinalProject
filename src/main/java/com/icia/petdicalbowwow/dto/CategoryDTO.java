package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("category")
public class CategoryDTO {
    private int catNo;
    private String catName;

    public static CategoryDTO toDTO(CategoryEntity entity){
        CategoryDTO category = new CategoryDTO();

        category.setCatNo(entity.getCatNo());
        category.setCatName(entity.getCatName());

        return category;
    }
}