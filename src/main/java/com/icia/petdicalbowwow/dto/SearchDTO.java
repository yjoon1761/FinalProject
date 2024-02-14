package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("search")
public class SearchDTO {
    private String category;
    private String keyword;
    private int catNo;
}
