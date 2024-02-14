package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    List<CommentEntity> findByBoa_bNo(int bNo);
}
