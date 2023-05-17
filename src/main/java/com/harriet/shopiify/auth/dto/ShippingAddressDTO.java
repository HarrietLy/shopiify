package com.harriet.shopiify.auth.dto;

import lombok.Data;

@Data
public class ShippingAddressDTO {

    private Long id;
    private Long userId;
    private String shippingAddress;
}
