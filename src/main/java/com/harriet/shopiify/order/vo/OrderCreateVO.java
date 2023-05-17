package com.harriet.shopiify.order.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Slf4j
public class OrderCreateVO {

    @NotNull
    private Double shippingFee;

    @NotNull
    private Long shippingAddressId;

    @NotNull
    @NotBlank
    private List<OrderItemVO> orderItems;

}
