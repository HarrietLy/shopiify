package com.harriet.shopiify.order.service;

import com.harriet.shopiify.auth.repository.ShippingAddressRepository;
import com.harriet.shopiify.cart.model.CartItemKey;
import com.harriet.shopiify.cart.repository.CartItemRepository;
import com.harriet.shopiify.order.model.Order;
import com.harriet.shopiify.order.model.OrderItem;
import com.harriet.shopiify.order.repository.OrderItemRepository;
import com.harriet.shopiify.order.repository.OrderRepository;
import com.harriet.shopiify.order.repository.OrderStatusRepository;
import com.harriet.shopiify.order.vo.OrderCreateVO;
import com.harriet.shopiify.order.vo.OrderItemVO;
import com.harriet.shopiify.product.model.Product;
import com.harriet.shopiify.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final ShippingAddressRepository shippingAddressRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;

    // TODO make thid a transaction so that any of the step fails, roll back
    public Long createOrder(OrderCreateVO vo){
        return null;
    }
    private Long saveOrder(OrderCreateVO vo){
    // save new order, save new order items, update order amount delete all cart items of the cartId, reduce stock of ordered items
        for ( OrderItemVO item : vo.getOrderItems()){
            // TODO: lock the variable stock so concurrent order creation on the same product is not allowed
            Long stock = productRepository.findById(item.getProductId()).get().getStock();
            if (item.getQuantity()> stock){
                throw new Exception("order creation cannot proceed, insufficient stock for "+ item.getProductId())
            }
        }
        Order createdOrder =orderRepository.save(toOrderEntity(vo));
        return createdOrder.getId();
    }

    private void saveOrderItems (OrderCreateVO vo){
        vo.getOrderItems().forEach(item -> orderItemRepository.save(toOrderItemEntity(item, saveOrder(vo))));
    }

    private void deleteCartItems (OrderCreateVO vo){
        vo.getOrderItems().forEach(item -> cartItemRepository.deleteById(toCartItemKey(vo.getCartId(),item.getProductId())));
    }
    private CartItemKey toCartItemKey (Long cartId, Long productId){
        CartItemKey cartItemKey = new CartItemKey();
        cartItemKey.setCartId(cartId);
        cartItemKey.setProductId(productId);
        return cartItemKey;
    }
    // TODO: reduce stocks of products

    private Order toOrderEntity (OrderCreateVO vo){
        Order entity = new Order();
        entity.setUserId(shippingAddressRepository.findById(vo.getShippingAddressId()).get().getUserId());
        entity.setShippingAddress(shippingAddressRepository.findById(vo.getShippingAddressId()).get());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setOrderStatus(orderStatusRepository.findById(1L).get());
//        List<OrderItem> orderItems = new ArrayList<>();
//        vo.getOrderItems().forEach(item-> orderItems.add(toOrderItemEntity(item)));
//        entity.setOrderItems(orderItems);
        Double totalAmount = 0.0;
        for (OrderItemVO item : vo.getOrderItems()){
            totalAmount = totalAmount + productRepository.findById(item.getProductId()).get().getPrice()*item.getQuantity();
        }
        log.info("total amount: {}", totalAmount);
        entity.setAmount(totalAmount);
        entity.setShippingFee(vo.getShippingFee());
        return entity;
    }

    private OrderItem toOrderItemEntity (OrderItemVO vo, Long orderId){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(orderRepository.findById(orderId).get());
        Product product =productRepository.findById(vo.getProductId()).get();
        orderItem.setPrice(product.getPrice());
        log.info("price captured at sale: {}", product.getPrice());
        orderItem.setProduct(product);
        orderItem.setQuantity(vo.getQuantity());
        return orderItem;
    }
}
