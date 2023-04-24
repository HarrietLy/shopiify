package com.harriet.shopiify.product.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductStatusVO {
//    private Long Id;

    @NotNull
    @NotBlank
    private String status;
}
