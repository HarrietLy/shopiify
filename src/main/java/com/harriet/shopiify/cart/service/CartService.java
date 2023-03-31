package com.harriet.shopiify.cart.service;

import com.harriet.shopiify.cart.dto.CartDTO;
import com.harriet.shopiify.cart.dto.CartItemDTO;
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
import java.util.ArrayList;
import java.util.List;
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
        return toCartDTO(entity);
    }

    private CartDTO toCartDTO ( Cart cart){
        List<CartItem> cartItemList = cart.getCartItems();
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        cartItemList.forEach(cartItem -> cartItemDTOList.add(toCartItemDTO(cartItem)));
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getId());
        cartDTO.setUserId(cart.getUserId());
        cartDTO.setCartItems(cartItemDTOList);
        return cartDTO;
    }

    private CartItemDTO toCartItemDTO (CartItem cartItem){
        CartItemDTO dto = new CartItemDTO();
        dto.setProductId(cartItem.getProduct().getId());
        dto.setCategory(cartItem.getProduct().getCategory().getCategory());
        dto.setProductStatus(cartItem.getProduct().getProductStatus().getStatus());
        dto.setDescription(cartItem.getProduct().getDescription());
        dto.setPrice(cartItem.getProduct().getPrice());
        dto.setProductName(cartItem.getProduct().getProductName());
        dto.setStock(cartItem.getProduct().getStock());
        dto.setUnits(cartItem.getProduct().getUnits());
        dto.setQuantity(cartItem.getQuantity());
        return dto;
    }

    private Long createCart(Long userId){
        LocalDateTime currentDateTime = LocalDateTime.now();
        Cart newCart =new Cart();
        newCart.setUserId(userId);
        newCart.setCreatedDate(currentDateTime);
        newCart.setLastModifiedDate(currentDateTime);
        log.info("newCart to create: {}", newCart);
        Cart createdCart = cartRepository.save(newCart);
        log.info("createdCart: {}", createdCart);
        return  createdCart.getId();
    }

    public CartItemKey addCartItemToCart(CartItemAddVO vo) throws Exception{
        Cart cartToAdd = cartRepository.findByUserId(vo.getUserId());
        Long cartIdToAdd=null;
        if ( cartToAdd== null){
            log.info("user does not have a cart yet, creating a new cart");
            cartIdToAdd = createCart(vo.getUserId());
        } else {
            cartIdToAdd=cartToAdd.getId();
        }
        vo.setCartId(cartIdToAdd);
        log.info("cartitem entity to save: {}",toCartItemEntity(vo));
        if (!enoughStock(vo)){
            throw new Exception("insufficient stock");
        }
        CartItem createdCartItem = cartItemRepository.save(toCartItemEntity(vo));
        return  createdCartItem.getId();
    }

    public void updateCartItem(CartItemUpdateVO vo) throws Exception{
        log.info("CartItemUpdateVO: {}", vo);
        CartItemKey cartItemKey = toCartItemKey(vo.getCartId(),vo.getProductId());
        CartItem cartItemToUpdate = cartItemRepository.findById(cartItemKey).orElseThrow(()->new NoSuchElementException("Resource not found"));
        log.info("cartItemToUpdate before copy from vo: {}",cartItemToUpdate.getQuantity());
        cartItemToUpdate.setQuantity(vo.getQuantity());
        log.info("cartItemToUpdate after copy from vo: {}",cartItemToUpdate.getQuantity());
        if (!enoughStock(vo)){
            throw new Exception("insufficient stock");
        }
        cartItemRepository.save(cartItemToUpdate);
    }

    private CartItem toCartItemEntity (CartItemAddVO vo){
        CartItem entity = new CartItem();
        Product product = productRepository.findById(vo.getProductId()).get();
        Cart cart = cartRepository.findById(vo.getCartId()).get();
        entity.setProduct(product);
        entity.setCart(cart);
        entity.setQuantity(vo.getQuantity());
        CartItemKey cartItemKey = toCartItemKey(vo.getCartId(),vo.getProductId());
        entity.setId(cartItemKey);
        log.info("cartItem entity after copy from vo: {}",entity);
        return entity;
    }

    private CartItem toCartItemEntity (CartItemUpdateVO vo){
        CartItem entity = new CartItem();
        Product product = productRepository.findById(vo.getProductId()).get();
        Cart cart = cartRepository.findById(vo.getCartId()).get();
        entity.setProduct(product);
        entity.setCart(cart);
        entity.setQuantity(vo.getQuantity());
        CartItemKey cartItemKey = toCartItemKey(vo.getCartId(),vo.getProductId());
        entity.setId(cartItemKey);
        log.info("cartItem entity after copy from vo: {}",entity);
        return entity;
    }

    @Transactional // TODO why do I have to add transactional annotation for delete method?
    public void deleteCartItem(Long cartId, Long productId){
        CartItemKey cartItemKey = toCartItemKey(cartId, productId);
        CartItem cartItemTDelete = cartItemRepository.findById(cartItemKey).orElseThrow(()->new NoSuchElementException("Resource not found"));
        cartItemRepository.deleteById(cartItemKey);
    }

    private CartItemKey toCartItemKey (Long cartId, Long productId){
        CartItemKey cartItemKey = new CartItemKey();
        cartItemKey.setCartId(cartId);
        cartItemKey.setProductId(productId);
        return cartItemKey;
    }

    private Boolean enoughStock ( CartItemAddVO vo){
        // Dont not need to lock stock in cart because concurrent cart are allowed to have the same stock even if the total qty in carts exceeds stock
        Long stock = productRepository.findById(vo.getProductId()).get().getStock();
        if (vo.getQuantity()>stock){
            return true;
        } else{
            return false;
        }
    }

    private Boolean enoughStock ( CartItemUpdateVO vo){
        // Dont not need to lock stock in cart because concurrent cart are allowed to have the same stock even if the total qty in carts exceeds stock
        Long stock = productRepository.findById(vo.getProductId()).get().getStock();
        if (vo.getQuantity()>stock){
            return true;
        } else{
            return false;
        }
    }
}
