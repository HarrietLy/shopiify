package com.harriet.shopiify.product.repository;

import com.harriet.shopiify.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
//    Optional<Category> findById(Long categoryId);
}
