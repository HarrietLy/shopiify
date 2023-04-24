package com.harriet.shopiify.product.controller;

import com.harriet.shopiify.product.dto.CategoryDTO;
import com.harriet.shopiify.product.service.CategoryService;
import com.harriet.shopiify.product.vo.CategoryVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @GetMapping()
    public List<CategoryDTO> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/{categoryId}")
    public CategoryDTO findById( @PathVariable("categoryId") Long id){
        return categoryService.findById(id);
    }

    @PostMapping()
    public Long save(@RequestBody CategoryVO vo){
        return categoryService.save(vo);
    }

    @PutMapping("{categoryId}")
    public void update(@PathVariable("categoryId") Long id, @RequestParam("newCategory") String category){
        categoryService.updateById(id, category);
    }

    @DeleteMapping("{categoryId}")
    public void delete(  Long id){
        categoryService.deleteById(id);
    }
}
