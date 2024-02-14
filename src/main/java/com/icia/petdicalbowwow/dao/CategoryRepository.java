package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.dto.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}