package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.VacationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VacationRepository extends JpaRepository<VacationEntity,Integer> {
    Optional<VacationEntity> deleteBycomNo(int comNo);

    List<VacationEntity> findAllBycomNo(int comNo);
}
