package com.harriet.shopiify.order.repository;

import com.harriet.shopiify.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);
    List<Order> findAll();
    Optional<Order> findByShippingAddressId(Long id);
}
