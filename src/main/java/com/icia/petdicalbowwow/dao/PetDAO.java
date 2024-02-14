package com.icia.petdicalbowwow.dao;


import com.icia.petdicalbowwow.dto.PetDTO;
import com.icia.petdicalbowwow.dto.SearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PetDAO {
    List<PetDTO> pSearchList(SearchDTO search);
}
