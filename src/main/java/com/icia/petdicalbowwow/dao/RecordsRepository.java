package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.RecordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordsRepository extends JpaRepository<RecordsEntity,Integer> {


    @Query("SELECT r FROM RecordsEntity r WHERE r.pet.mem.MNO = :mNo")
    List<RecordsEntity> findByMemMNO(int mNo);

//    List<RecordsEntity> findByComComNo(int comNo);

    List<RecordsEntity> findByCom_comNo(int comNo);

    @Query("SELECT r FROM RecordsEntity r WHERE r.com.comNo = :comNo AND r.rDate= :dateString")
    List<RecordsEntity> findAllByrDate(String dateString, int comNo);

    @Query("SELECT r FROM RecordsEntity r WHERE r.pet.mem.MNO = :mNo AND r.rDate= :dateString")
    List<RecordsEntity> findAllByrDate1(String dateString, int mNo);

}
