package com.harriet.shopiify.cart.model;

import com.harriet.shopiify.cart.dto.CartItemDTO;
import com.harriet.shopiify.cart.vo.CartItemAddVO;
import com.harriet.shopiify.order.model.Order;
import com.harriet.shopiify.product.model.Product;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="cart_item")
public  class CartItem {
    @EmbeddedId
    private CartItemKey id;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private Long quantity;

    public CartItemDTO toCartItemDTO (){
        CartItemDTO dto = new CartItemDTO();
        dto.setProductId(this.getProduct().getId());
        dto.setCategory(this.getProduct().getCategory().getCategory());
        dto.setProductStatus(this.getProduct().getProductStatus().getStatus());
        dto.setDescription(this.getProduct().getDescription());
        dto.setPrice(this.getProduct().getPrice());
        dto.setProductName(this.getProduct().getProductName());
        dto.setStock(this.getProduct().getStock());
        dto.setUnits(this.getProduct().getUnits());
        dto.setQuantity(this.getQuantity());
        return dto;
    }

    public CartItem  (Product product, Cart cart, Long quantity ){
        this.product =product;
        this.cart = cart;
        this.quantity=quantity;
        this.id = new CartItemKey(cart.getId(),product.getId());;
    }

    public CartItem(){}
}
