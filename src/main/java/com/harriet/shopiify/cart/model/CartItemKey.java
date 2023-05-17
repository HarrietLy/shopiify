package com.harriet.shopiify.cart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Embeddable
@Data
public class CartItemKey {

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "product_id")
    private Long productId;

    public CartItemKey(Long cartId, Long productId) {
        this.cartId = cartId;
        this.productId = productId;
    }

    public CartItemKey(){};
}
