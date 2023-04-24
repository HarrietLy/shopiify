package com.harriet.shopiify.product.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CategoryVO {
    @NotNull
    @NotBlank
    private String category;
}
