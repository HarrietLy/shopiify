package com.harriet.shopiify.cart.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CartItemUpdateVO {
    @NotNull
    @NotBlank
    private Long cartId;

    @NotNull
    @NotBlank
    private Long productId;

    // TODO check where validation is not working
    @NotNull
    @Min(value = 0L, message = "The value must be positive")
    private Long quantity;
}
