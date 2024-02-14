package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.VaccinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationRepository extends JpaRepository <VaccinationEntity,Integer> {
}