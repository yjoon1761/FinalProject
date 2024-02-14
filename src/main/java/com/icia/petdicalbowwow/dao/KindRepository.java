package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.KindEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KindRepository extends JpaRepository<KindEntity,Integer> {
    @Query("SELECT k FROM KindEntity k ORDER BY k.kName ASC")
    List<KindEntity> findAllAcs();
}

