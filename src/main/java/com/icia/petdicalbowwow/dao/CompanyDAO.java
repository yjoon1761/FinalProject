package com.icia.petdicalbowwow.dao;


import com.icia.petdicalbowwow.dto.CompanyDTO;
import com.icia.petdicalbowwow.dto.SearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyDAO {

    List<CompanyDTO> cSearchList(SearchDTO search);

    String cIdFind(String findIdEmail, String findIdPhone);

    String cIdFind2(String findPwId, String findPwEmail);
}

