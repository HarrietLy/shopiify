package com.harriet.shopiify.auth.repository;

import com.harriet.shopiify.auth.model.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ShippingAddressRepository  extends JpaRepository<ShippingAddress, Long> {
    List<ShippingAddress> findByUserId(Long userId);
}
