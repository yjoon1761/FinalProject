package com.icia.petdicalbowwow.dao;


import com.icia.petdicalbowwow.dto.AllergyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergyRepository extends JpaRepository<AllergyEntity,Integer> {
}

