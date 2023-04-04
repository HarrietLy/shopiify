package com.harriet.shopiify.product.controller;

import com.harriet.shopiify.product.dto.ProductDTO;
import com.harriet.shopiify.product.service.ProductService;
import com.harriet.shopiify.product.vo.ProductVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @GetMapping
    public List<ProductDTO> findAllProducts(){
        return productService.findAllProducts();
    }

    @PostMapping
    public Long save(@RequestBody  ProductVO vo){
        return productService.createProduct(vo);
    }


}
