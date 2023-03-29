package com.harriet.shopiify.cart.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CartDTO {
    private long cartId;
    private long userId;
    private List<CartItemDTO> cartItems;

}
