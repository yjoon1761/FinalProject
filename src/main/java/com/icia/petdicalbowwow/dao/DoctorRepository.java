package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Integer> {

    Optional<DoctorEntity> findAllBydId(int dId);

//    @Query("SELECT d FROM DoctorEntity d WHERE d.com.comNo = :comNo")
//    List<DoctorEntity> findAllByComNo(int comNo);
//
//    @Query("SELECT d FROM DoctorEntity d WHERE d.com.comNo = :comNo")
    List<DoctorEntity> findByCom_comNo(int comNo);

    @Query("SELECT d.dId FROM DoctorEntity d WHERE d.com.comNo = :comNo AND d.dId = :dId")
    Optional<DoctorEntity> findByComNo(int comNo, int dId);
}