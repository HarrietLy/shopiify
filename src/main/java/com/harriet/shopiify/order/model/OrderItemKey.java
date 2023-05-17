package com.harriet.shopiify.order.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class OrderItemKey implements Serializable {

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    public OrderItemKey(Long orderId, Long productId){
        this.orderId =orderId;
        this.productId=productId;
    }

    public OrderItemKey(){
    }


}
