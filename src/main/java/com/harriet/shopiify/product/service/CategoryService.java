package com.harriet.shopiify.product.service;

import com.harriet.shopiify.product.dto.CategoryDTO;
import com.harriet.shopiify.product.model.Category;
import com.harriet.shopiify.product.repository.CategoryRepository;
import com.harriet.shopiify.product.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll (){
        return categoryRepository.findAll().stream().map(bean-> toDTO(bean)).collect(Collectors.toList());
    }

    public Long save (CategoryVO vo){
        return categoryRepository.save(toEntity(vo)).getId();
    }

    public CategoryDTO findById(Long Id){
        return toDTO(categoryRepository.findById(Id).get());
    }

    public void updateById(Long Id, String category){
        Category entity = categoryRepository.findById(Id).get();
        entity.setCategory(category);
    };

    public void deleteById(Long Id){
        categoryRepository.deleteById(Id);
    }

    private CategoryDTO toDTO (Category original){
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(original, dto);
        return dto;
    }

    private Category toEntity(CategoryVO vo){
        Category entity = new Category();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }
}
