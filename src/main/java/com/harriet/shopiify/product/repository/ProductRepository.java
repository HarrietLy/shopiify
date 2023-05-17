package com.harriet.shopiify.product.repository;

import com.harriet.shopiify.product.model.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    @Lock(LockModeType.PESSIMISTIC_WRITE) // REMOVE due to ERROR: cannot execute SELECT FOR NO KEY UPDATE in a read-only transaction in addtoCart
//    TODO: how to ? no concurrent reduction of stocks allowed, as well as no thread can read the stock info while a new stock is updated
    Optional<Product> findById(Long productId);

//    @Query(nativeQuery = true, value="select * from product p where 1=1"
////            +" and if(:productName !='', p.product_name = concat('%',:productName,'%'), true)"
//            +" and if(:productName !='', p.product_name = :productName , true)"
//            +" and if(:categoryId !=0 , p.category_id = categoryId, true)"
//            +" and if(:productStaId !=0, p.product_status_id = productStaId, true)"
////            +" and if(:stock !=0, p.stock = stock, true)"
//    )
//    List<Product> findByCondition(@Param("productName") String productName,
//                                  @Param("categoryId") Long categoryId,
//                                  @Param("productStaId") Long productStatusId);
}
