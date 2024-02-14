package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.DiseaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepository extends JpaRepository<DiseaseEntity,Integer> {
}


