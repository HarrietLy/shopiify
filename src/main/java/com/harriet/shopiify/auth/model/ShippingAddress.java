package com.harriet.shopiify.auth.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="shipping_address")
@Data
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id")
//    @JoinColumn(name="user_id")
    private Long userId;

    @Column(name="shipping_address")
    private String shippingAddress;
}
