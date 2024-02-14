package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.RecordsDTO;
import com.icia.petdicalbowwow.dto.SearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordsDAO {
    List<RecordsDTO> rSearchList(SearchDTO search);
}