package com.harriet.shopiify.cart.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class CartDTO {
    private long cartId;
    private long userId;
    private LocalDateTime createdDate;
    private List<CartItemDTO> cartItems;

}
