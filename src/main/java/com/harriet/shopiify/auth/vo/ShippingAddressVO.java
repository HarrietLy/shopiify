package com.harriet.shopiify.auth.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ShippingAddressVO {
    @NotNull
    private Long userId;

    @NotNull
    @NotBlank
    private String shippingAddress;
}
