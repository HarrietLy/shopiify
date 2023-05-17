package com.harriet.shopiify.order.dto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private Long id;

    private Long userId;

    private Double productSubtotal;

    private Double shippingFee;

    private String shippingAddress;

    private List<OrderItemDTO> orderItems;

    private LocalDateTime createdDate;

    private String orderStatus;


}

