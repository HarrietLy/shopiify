package com.harriet.shopiify.cart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CartDTO {
    private long cartId;
    private long userId;

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") // TODO: mapping to date does not work, why?
    private LocalDateTime createdDate;

    private List<CartItemDTO> cartItems;

}
