package com.harriet.shopiify.cart.model;

import com.harriet.shopiify.cart.dto.CartDTO;
import com.harriet.shopiify.cart.dto.CartItemDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    @JoinColumn(name="user_id")
    @Column(unique = true)
    private Long userId;

    private LocalDateTime createdDate;

//    private LocalDateTime lastModifiedDate;

    @OneToMany
    @JoinColumn(name="cart_id")
    private List<CartItem> cartItems= new ArrayList<>();

    public CartDTO toCartDTO (){
        List<CartItem> cartItemList = this.getCartItems();
        List<CartItemDTO> cartItemDTOList = cartItemList.stream().map(cartItem -> cartItem.toCartItemDTO()).collect(Collectors.toList());
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(this.getId());
        cartDTO.setUserId(this.getUserId());
        cartDTO.setCartItems(cartItemDTOList);
        cartDTO.setCreatedDate(this.getCreatedDate());
        return cartDTO;
    }

    public Cart(){}

    public Cart(Long userId, LocalDateTime createdDate){
        this.userId=userId;
        this.createdDate=createdDate;
    }
}
