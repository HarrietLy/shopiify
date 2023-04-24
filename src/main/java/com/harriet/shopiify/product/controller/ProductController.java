package com.harriet.shopiify.product.controller;

import com.harriet.shopiify.product.dto.ProductDTO;
import com.harriet.shopiify.product.service.ProductService;
import com.harriet.shopiify.product.vo.ProductQueryVO;
import com.harriet.shopiify.product.vo.ProductUpdateVO;
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

    @GetMapping("{productId}")
    public ProductDTO findById(Long id){
        return productService.findProductById(id);
    }

    @PostMapping
    public Long save(@RequestBody  ProductVO vo){
        return productService.createProduct(vo);
    }

    @PutMapping("{productId}")
    public void update(@PathVariable("productId") Long id,  @RequestBody ProductUpdateVO vo){
         productService.updateProduct(id, vo);
    }

    @PostMapping("/query")
    public List<ProductDTO> query(@RequestBody ProductQueryVO vo){
        return productService.query(vo);
    }

    @DeleteMapping("{productId}")
    public void delete(  Long id){
        productService.deleteProduct(id);
    }
}
