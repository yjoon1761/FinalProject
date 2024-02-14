package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.CardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Integer> {
    Optional<CardEntity> findByCrdNum(String crdNum);

    @Modifying
    @Transactional
    @Query("UPDATE CardEntity c SET c.crdId = :keyValue, c.crdCheck = 1 WHERE c.crdNo = :crdNo AND c.pet.pNo = :pNo")
    void updateValue(int crdNo, int pNo, String keyValue);

    @Query("SELECT c FROM CardEntity c WHERE c.crdCheck = 0")
    List<CardEntity> findAllByCrdCheck();

    @Query("SELECT c FROM CardEntity c WHERE c.crdId = :keyVal")
    CardEntity findByCrdId(String keyVal);

    @Query("SELECT c FROM CardEntity c WHERE c.pet.mem.MNO = :mNo")
    List<CardEntity> findAllByCrdCheckMNo(int mNo);
}
