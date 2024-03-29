package com.harriet.shopiify.cart.controller;

import com.harriet.shopiify.cart.dto.CartDTO;
import com.harriet.shopiify.cart.model.CartItem;
import com.harriet.shopiify.cart.model.CartItemKey;
import com.harriet.shopiify.cart.service.CartService;
import com.harriet.shopiify.cart.vo.CartItemAddVO;
import com.harriet.shopiify.cart.vo.CartItemUpdateVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cart")
@Tag(name="cart")
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService){
        this.cartService  = cartService;
    }

    @GetMapping("/user/{userId}")
    public CartDTO findCartItemByCartId(@PathVariable("userId") Long userId){
        return cartService.getCartByUserId(userId);
    }

    @PostMapping("/addToCart")
    public CartItemKey addCartItemToCart(@RequestBody @Valid CartItemAddVO vo) {
        return cartService.addCartItemToCart(vo);
    }

    @PutMapping("/cartItem")
    public void updateCartItem(@RequestBody @Valid CartItemUpdateVO vo) throws Exception{
        try {
            cartService.updateCartItem(vo);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping("/cartItem")
    public void deleteCartItem(@RequestParam("cartId") Long cartId, @RequestParam("productId") Long productId){
        cartService.deleteCartItem(cartId, productId);
    }

}
