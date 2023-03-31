package com.harriet.shopiify.order.repository;

import com.harriet.shopiify.order.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
}
