package com.harriet.shopiify.order.model;

import com.harriet.shopiify.product.model.Product;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="order_item")
public class OrderItem {
    @EmbeddedId
    private OrderItemKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private Long quantity;

    private Double price;


}
