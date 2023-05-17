package com.harriet.shopiify.cart.repository;

import com.harriet.shopiify.cart.model.CartItem;
import com.harriet.shopiify.cart.model.CartItemKey;
import com.harriet.shopiify.cart.vo.CartItemAddVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository   <CartItem, CartItemKey> {
    Optional<CartItem> findById(CartItemKey cartItemKey);
    void deleteById ( CartItemKey cartItemKey);
    CartItem save (CartItemAddVO VO);
}
