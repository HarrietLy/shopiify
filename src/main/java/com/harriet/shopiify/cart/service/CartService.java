package com.harriet.shopiify.cart.service;

import com.harriet.shopiify.cart.dto.CartDTO;
import com.harriet.shopiify.cart.model.Cart;
import com.harriet.shopiify.cart.model.CartItem;
import com.harriet.shopiify.cart.model.CartItemKey;
import com.harriet.shopiify.cart.repository.CartItemRepository;
import com.harriet.shopiify.cart.vo.CartItemAddVO;
import com.harriet.shopiify.cart.repository.CartRepository;
import com.harriet.shopiify.cart.vo.CartItemUpdateVO;
import com.harriet.shopiify.product.model.Product;
import com.harriet.shopiify.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService (CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository){
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public CartDTO getCartByUserId (Long userId){
        Cart entity = cartRepository.findByUserId(userId);
        return entity.toCartDTO();
    }

    public CartItem getCartItemById (Long cartId, Long productId){
        CartItem cartItem = cartItemRepository.findById(new CartItemKey(cartId, productId)).orElseThrow(()->new NoSuchElementException("Resource not found"));
        return cartItem;
    }


    private Long createCart(Long userId){
        LocalDateTime currentDateTime = LocalDateTime.now();
        log.info("currentDateTime: {}",currentDateTime );
        Cart createdCart = cartRepository.save(new Cart(userId, currentDateTime));
        log.info("createdCart id: {}", createdCart.getId());
        return  createdCart.getId();
    }

    public CartItemKey addCartItemToCart(CartItemAddVO vo) {
        Cart cartToAdd = cartRepository.findByUserId(vo.getUserId());
        Long cartIdToAdd=null;
        if ( cartToAdd== null){
            log.info("user does not have a cart yet, creating a new cart");
            cartIdToAdd = createCart(vo.getUserId());
        } else {
            cartIdToAdd=cartToAdd.getId();
        }
        vo.setCartId(cartIdToAdd);
        if (!enoughStock(vo)){
            throw new RuntimeException("insufficient stock");
        }
        Product product = productRepository.findById(vo.getProductId()).get();
        Cart cart = cartRepository.findById(vo.getCartId()).get();
        CartItem createdCartItem = cartItemRepository.save( new CartItem(product, cart, vo.getQuantity()));
        return  createdCartItem.getId();
    }

    public void updateCartItem(CartItemUpdateVO vo) throws Exception{
        log.info("CartItemUpdateVO: {}", vo);
        CartItem cartItemToUpdate = getCartItemById(vo.getCartId(),vo.getProductId());
        log.info("cartItemToUpdate before copy from vo: {}",cartItemToUpdate.getQuantity());
        cartItemToUpdate.setQuantity(vo.getQuantity());
        log.info("cartItemToUpdate after copy from vo: {}",cartItemToUpdate.getQuantity());
        if (!enoughStock(vo)){
            throw new Exception("insufficient stock");
        }
        cartItemRepository.save(cartItemToUpdate);
    }


    @Transactional
    public void deleteCartItem(Long cartId, Long productId){
        cartItemRepository.deleteById(new CartItemKey(cartId,productId));
    }

    private Boolean enoughStock ( CartItemAddVO vo){
        // Dont not need to lock stock in cart because concurrent cart are allowed to have the same stock even if the total qty in carts exceeds stock
        Long stock = productRepository.findById(vo.getProductId()).get().getStock();
        log.info("stock before add to cart: {}", stock);
        log.info("quantity in cart: {}", vo.getQuantity());
        if (vo.getQuantity() <= stock){
            return true;
        } else{
            return false;
        }
    }

    private Boolean enoughStock ( CartItemUpdateVO vo){
        Long stock = productRepository.findById(vo.getProductId()).get().getStock();
        log.info("current stock: {}", stock);
        if (vo.getQuantity()<=stock){
            return true;
        } else{
            return false;
        }
    }
}

//    private CartItem toCartItemEntity (CartItemAddVO vo){
//        CartItem entity = new CartItem();
//        Product product = productRepository.findById(vo.getProductId()).get();
//        Cart cart = cartRepository.findById(vo.getCartId()).get();
//        entity.setProduct(product);
//        entity.setCart(cart);
//        entity.setQuantity(vo.getQuantity());
//        CartItemKey cartItemKey =  new CartItemKey(vo.getCartId(),vo.getProductId());
//        entity.setId(cartItemKey);
//        return entity;
//    }

