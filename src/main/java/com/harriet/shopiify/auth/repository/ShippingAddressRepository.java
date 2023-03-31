package com.harriet.shopiify.auth.repository;

import com.harriet.shopiify.auth.model.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingAddressRepository  extends JpaRepository<ShippingAddress, Long> {
    Optional<ShippingAddress> findById(Long shippingAddressId);
}
