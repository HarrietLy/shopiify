package com.harriet.shopiify.order.vo;

import com.harriet.shopiify.cart.dto.CartItemDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderCreateVO {

//    @NotNull
//    private Long cartId;

    @NotNull
    private Double shippingFee;

    @NotNull
    private Long shippingAddressId;

    @NotNull
    @NotBlank
    private List<OrderItemVO> orderItems;

}
