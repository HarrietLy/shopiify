package com.harriet.shopiify.product.service;

import com.harriet.shopiify.product.dto.ProductDTO;
import com.harriet.shopiify.product.model.Category;
import com.harriet.shopiify.product.model.Product;
import com.harriet.shopiify.product.model.ProductStatus;
import com.harriet.shopiify.product.repository.CategoryRepository;
import com.harriet.shopiify.product.repository.ProductRepository;
import com.harriet.shopiify.product.repository.ProductStatusRepository;
import com.harriet.shopiify.product.vo.ProductUpdateVO;
import com.harriet.shopiify.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductStatusRepository productStatusRepository;

    public ProductService (ProductRepository productRepository, CategoryRepository categoryRepository, ProductStatusRepository productStatusRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productStatusRepository = productStatusRepository;
    }

    public Long createProduct(ProductVO vo){
        log.info("productVO: {}", vo);
        Product createdEntity = productRepository.save(toEntity(vo));
        log.info("createdEntity: {}", createdEntity);
        return createdEntity.getId();
    }


    public void deleteProduct(long productId){
        productRepository.deleteById(productId);
    }

    public void updateProduct(long productId, ProductUpdateVO vo){
        Product entityToUpdate = productRepository.findById(productId).orElseThrow(()->new NoSuchElementException("No product found"));
        BeanUtils.copyProperties(toEntity(vo),entityToUpdate);
    }

    public ProductDTO findProductById(long productId){
        Product entity = productRepository.findById(productId).orElseThrow(()->new NoSuchElementException("No product found"));
        return toDTO(entity);
    }

    public List<ProductDTO> findAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(bean->toDTO(bean)).collect(Collectors.toList());
    }

    private Product toEntity(ProductVO vo){
        Product entity = new Product();
        BeanUtils.copyProperties(vo, entity);
        log.info("Entity after copy properties from vo: {}", entity);
        Category category = categoryRepository.findById(vo.getCategoryId()).orElseThrow(()-> new NoSuchElementException("Category id not found"));
        ProductStatus productStatus = productStatusRepository.findById(vo.getProductStatusId()).orElseThrow(()-> new NoSuchElementException("Product status id not found"));
        entity.setCategory(category);
        entity.setProductStatus(productStatus);
        log.info("entity after setting Cat and Product status: {}", entity);
        return entity;
    }

    private ProductDTO toDTO (Product original){
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(original, dto);
        log.info("dto after copying bean properties: {}", dto);
        dto.setCategory(original.getCategory().getCategory());
        dto.setProductStatus(original.getProductStatus().getStatus());
        log.info("dto after setting Cat and ProductStatus: {}", dto);
        return dto;
    }
}
