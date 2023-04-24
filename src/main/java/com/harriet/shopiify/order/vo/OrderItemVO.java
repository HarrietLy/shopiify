package com.harriet.shopiify.order.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderItemVO {

    @NotNull
    private Long cartId;

    @NotNull
    private Long productId;
}
