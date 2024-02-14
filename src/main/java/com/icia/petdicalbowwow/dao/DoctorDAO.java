package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.DoctorDTO;
import com.icia.petdicalbowwow.dto.SearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DoctorDAO {
    List<DoctorDTO> dSearchList(SearchDTO search);

    String dIdCheck(int dId);
}