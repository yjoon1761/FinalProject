package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.MemberDTO;
import com.icia.petdicalbowwow.dto.SearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDAO {
    List<MemberDTO> mSearchList(SearchDTO search);

    String mIdFind(String findIdEmail, String findIdPhone);

    String mIdFind2(String findPwId, String findPwEmail);
}
