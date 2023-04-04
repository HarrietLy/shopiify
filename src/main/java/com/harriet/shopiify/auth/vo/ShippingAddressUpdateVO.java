package com.harriet.shopiify.auth.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ShippingAddressUpdateVO {

    @NotNull
    @NotBlank
    private String shippingAddress;
}
