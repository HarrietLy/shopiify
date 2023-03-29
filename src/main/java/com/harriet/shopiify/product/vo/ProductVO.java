package com.harriet.shopiify.product.vo;

import com.harriet.shopiify.product.model.Category;
import com.harriet.shopiify.product.model.ProductStatus;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;

@Data
public class ProductVO {

    @NotNull
    @NotBlank
    private String productName;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long productStatusId;

    @NotNull
    @NotBlank
    private String units;

    @NotNull
    @Min(value = 0L, message = "The value must be positive")
    private BigInteger stock;

    @NotNull
    @Min(value = 0, message = "The value must be positive")
    private Double price;
}
