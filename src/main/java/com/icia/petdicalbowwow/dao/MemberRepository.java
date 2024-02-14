package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    Optional<MemberEntity> findByMID(String MID);
    Optional<MemberEntity> findByNickName(String nickName);
//    Optional<MemberEntity> findById(String loginId);
}