package com.harriet.shopiify.product.controller;

import com.harriet.shopiify.product.dto.CategoryDTO;
import com.harriet.shopiify.product.service.CategoryService;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters=false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;


    @Test
    public void contextLoads() throws Exception {
        assertThat(categoryService).isNotNull();
    }

    @Test
    void givenCategories_whenGetCategory_thenStatus200() throws Exception{
        CategoryDTO cat1 = new CategoryDTO();
        cat1.setCategory("Fruits");
        CategoryDTO cat2 = new CategoryDTO();
        cat2.setCategory("Vegetables");
        List<CategoryDTO> allCategories = new ArrayList<>();
        allCategories.add(cat1);
        allCategories.add(cat2);
        given(categoryService.findAll()).willReturn(allCategories);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/category").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].category").value(cat1.getCategory()))
                .andExpect(jsonPath("$[1].category").value(cat2.getCategory()));
    }

    @Test
    void findById() throws Exception{
        CategoryDTO cat = new CategoryDTO();
        cat.setCategory("Fruits");
        cat.setId(1L);
        given(categoryService.findById(1L)).willReturn(cat);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/category/{id}",cat.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString().contains(cat.getCategory());
    }

}