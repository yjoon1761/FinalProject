package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReservationRepository extends JpaRepository<ReservationEntity,Integer> {

    @Query("SELECT r FROM ReservationEntity r WHERE r.com.comNo = :comNo ORDER BY r.resTime")
    List<ReservationEntity> findAllByComNo(int comNo);

    @Query("SELECT r FROM ReservationEntity r WHERE r.pet.mem.mName LIKE %:keyword% AND r.resDate = :selectedDate")
    List<ReservationEntity> findAllByKeyword(String keyword, String selectedDate);

    @Query("SELECT r FROM ReservationEntity r WHERE r.pet.mem.MNO = :mno ORDER BY r.resDate ASC")
    List<ReservationEntity> findAllByMember(int mno);

    @Modifying
    @Query("DELETE FROM ReservationEntity r WHERE r.resDate = :resDate AND r.resTime = :resTime AND r.com.cName = :cname")
    void deleteByVals(String resDate, String resTime, String cname);

    @Query("SELECT r FROM ReservationEntity r WHERE r.resDate = :resDate AND r.com.comNo = :comNo")
    List<ReservationEntity> findAllByResDate(String resDate, int comNo);

    List<ReservationEntity> findAllByResDate(String today);
}