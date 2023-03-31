package com.harriet.shopiify.order.repository;

import com.harriet.shopiify.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
