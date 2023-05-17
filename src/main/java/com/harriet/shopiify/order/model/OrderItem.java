package com.harriet.shopiify.order.model;

import com.harriet.shopiify.cart.model.CartItem;
import com.harriet.shopiify.order.dto.OrderItemDTO;
import com.harriet.shopiify.product.model.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Entity
@Table(name="order_item")
@Slf4j
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

    public OrderItem (Order order, Product product, CartItem cartItem){
        this.order = order;
        this.price = product.getPrice();
        log.info("price captured at sale: {}", product.getPrice());
        this.product = product;
        this.quantity = cartItem.getQuantity();
        this.id = new OrderItemKey(order.getId(), product.getId());
    }

    public OrderItem (){};

    public OrderItemDTO toOrderItemDTO (){
        OrderItemDTO dto = new OrderItemDTO();
        dto.setOrderId(this.order.getId());
        dto.setProductId(this.product.getId());
        dto.setPrice(this.price);
        dto.setQuantity(this.quantity);
        return dto;
    }
}
