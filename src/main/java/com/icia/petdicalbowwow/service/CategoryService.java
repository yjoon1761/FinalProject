package com.icia.petdicalbowwow.service;

import com.icia.petdicalbowwow.dao.CategoryRepository;
import com.icia.petdicalbowwow.dto.CategoryDTO;
import com.icia.petdicalbowwow.dto.CategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository catrepo;

    public List<CategoryDTO> categoryList() {
        List<CategoryDTO> list = new ArrayList<>();

        List<CategoryEntity> entityList = catrepo.findAll();

        for (CategoryEntity entity : entityList){
            list.add(CategoryDTO.toDTO(entity));
        }

        return list;
    }
}