package com.harriet.shopiify.product.repository;

import com.harriet.shopiify.product.model.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    // no concurrent reduction of stocks allowed, as well as no thread can read the stock info while a new stock is updated
    Optional<Product> findById(Long productId);

    @Query(nativeQuery = true, value="select * from product where 1=1"
            +" and if(:productName is not null, product_name like concat('%',:productName,'%'), true)"
            +" and if(:categoryId is not null, category_id = categoryId, true)"
            +" and if(:productStatusId is not null, product_status_id = productStatusId, true)"
            +" and if(:stock is not null, stock = stock, true)"
    )
    List<Product> findByCondition(String productName, Long categoryId, Long productStatusId, Long stock);
}
