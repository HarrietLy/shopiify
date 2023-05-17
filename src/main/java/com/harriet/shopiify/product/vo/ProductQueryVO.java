package com.harriet.shopiify.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class ProductQueryVO implements Serializable {

    private String productName;

    private Long categoryId;

    private Long productStatusId;

    private Long stock;

}
