package com.harriet.shopiify.cart.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    @JoinColumn(name="user_id")
    @Column(unique = true)
    private Long userId;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    @OneToMany
    @JoinColumn(name="cart_id")
    private List<CartItem> cartItems= new ArrayList<>();


}
