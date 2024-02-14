package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<PetEntity,Integer> {

    List<PetEntity> findByMemMNO(int mNo);

    @Query("SELECT p FROM PetEntity p WHERE p.pNo = :pNo AND p.mem.MNO = :MNO")
    PetEntity findByPNoAndMNO(int pNo, int MNO);

    Optional<PetEntity> findBymemMNO(int mno);

    @Query("SELECT p FROM PetEntity p WHERE p.pName = :keyword")
    List<PetEntity> findByPName(String keyword);

    @Query("SELECT p FROM PetEntity p WHERE p.mem.MNO = :mno")
    List<PetEntity> findByMNO(int mno);
}
