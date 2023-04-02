package com.harriet.shopiify.order.dto;

import lombok.Data;

@Data
public class OrderItemDTO {

    private Long orderId;

    private Long productId;

    private Long quantity;

    private Double price;
}
