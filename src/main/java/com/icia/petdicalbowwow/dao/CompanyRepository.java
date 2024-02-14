package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity,Integer> {

    Optional<CompanyEntity> findBycId(String cId);

    Optional<CompanyEntity> findBycEmail(String cEmail);

}