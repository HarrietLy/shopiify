package com.harriet.shopiify.product.repository;

import com.harriet.shopiify.product.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductStatusRepository extends JpaRepository<ProductStatus, Long> {
//    Optional<ProductStatus> findById(Long Id);
}
