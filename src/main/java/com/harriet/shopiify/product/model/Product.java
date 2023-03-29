package com.harriet.shopiify.product.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigInteger;

@Data
@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(unique = true)
    private String productName;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_status_id")
    private ProductStatus productStatus;

    private String units;

    @Min(value = 0L, message = "The value must be a positive integer")
    private BigInteger stock;

    @Min(value = 0, message = "The value must be a positive number")
    private Double price;
}
