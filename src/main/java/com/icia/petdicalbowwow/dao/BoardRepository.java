package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.BoardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE BoardEntity b SET b.bHit = b.bHit + 1 WHERE b.bNo = :bNo")
    void bHit(int bNo);

    @Query("SELECT b FROM BoardEntity b WHERE b.cat.catNo = :catNo ORDER BY b.bNo DESC")
    List<BoardEntity> findAllByCatNoOrderByBNoDesc(int catNo);

    @Query("SELECT b FROM BoardEntity b WHERE b.bTitle LIKE %:keyword% AND b.cat.catNo = :catNo")
    List<BoardEntity> findBybTitleContainingAndCatNo(String keyword, int catNo);

    @Query("SELECT b FROM BoardEntity b WHERE b.bContent LIKE %:keyword% AND b.cat.catNo = :catNo")
    List<BoardEntity> findBybContentContainingAndCatNo(String keyword, int catNo);

    @Query("SELECT b FROM BoardEntity b WHERE (b.mem IN (SELECT m.MNO FROM MemberEntity m WHERE m.nickName LIKE '%'||:keyword||'%') OR b.com IN (SELECT c.comNo FROM CompanyEntity c WHERE c.cName LIKE '%'||:keyword||'%')) AND b.cat.catNo = :catNo")
    List<BoardEntity> findA(String keyword, int catNo);

    @Query("SELECT b FROM BoardEntity b WHERE b.mem.MNO = :MNO ORDER BY b.bNo DESC")
    List<BoardEntity> findAllByMNOOrderByBNoDesc(int MNO);

    @Query("SELECT b FROM BoardEntity b WHERE b.com.comNo = :comNo ORDER BY b.bNo DESC")
    List<BoardEntity> findAllByComNoOrderByBNoDesc(int comNo);
}
