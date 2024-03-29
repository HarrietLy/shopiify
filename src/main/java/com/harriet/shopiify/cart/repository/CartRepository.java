package com.harriet.shopiify.cart.repository;

import com.harriet.shopiify.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findById(Long cartId);
    Cart findByUserId(Long userId);
}
