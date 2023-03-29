package com.harriet.shopiify.product.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="product_status")
public class ProductStatus {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String status;
}
