package com.harriet.shopiify.product.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String category;
}
