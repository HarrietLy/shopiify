package com.harriet.shopiify.order.model;

import com.harriet.shopiify.auth.model.ShippingAddress;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="customer_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;

    private Double amount;

    private Double shippingFee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id")
    private ShippingAddress shippingAddress;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderDetails;

    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name="order_status_id")
    private OrderStatus orderStatus;
}
