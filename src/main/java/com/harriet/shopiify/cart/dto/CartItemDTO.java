package com.harriet.shopiify.cart.dto;

import com.harriet.shopiify.product.dto.ProductDTO;
import lombok.Data;

import java.math.BigInteger;

@Data
public class CartItemDTO {

    private Long productId;

    private String productName;

    private String description;

    private String category;

    private String productStatus;

    private String units;

    private BigInteger stock;

    private Double price;

    private Long quantity;
}
