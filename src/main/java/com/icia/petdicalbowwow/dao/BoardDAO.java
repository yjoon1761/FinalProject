package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.BoardDTO;
import com.icia.petdicalbowwow.dto.SearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDAO {
    List<BoardDTO> bSearchList(SearchDTO search);
}
