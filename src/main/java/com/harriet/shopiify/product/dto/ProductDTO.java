package com.harriet.shopiify.product.dto;

import com.harriet.shopiify.product.model.Category;
import com.harriet.shopiify.product.model.ProductStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class ProductDTO implements Serializable {
    private Long id;

    private String productName;

    private String description;

    private String category;

    private String productStatus;

    private String units;

    private BigInteger stock;

    private Double price;
}
