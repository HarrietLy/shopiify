package com.harriet.shopiify.cart.model;

import com.harriet.shopiify.order.model.Order;
import com.harriet.shopiify.product.model.Product;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="cart_item")
public class CartItem {
    @EmbeddedId
    private CartItemKey id;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private Long quantity;
}
