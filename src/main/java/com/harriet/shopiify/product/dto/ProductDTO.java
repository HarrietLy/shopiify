package com.harriet.shopiify.product.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDTO implements Serializable {
    private Long id;

    private String productName;

    private String description;

    private String category;

    private String productStatus;

    private String units;

    private Long stock;

    private Double price;
}
