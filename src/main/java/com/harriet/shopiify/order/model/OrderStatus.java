package com.harriet.shopiify.order.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="order_status")
@Data
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String status;
}
