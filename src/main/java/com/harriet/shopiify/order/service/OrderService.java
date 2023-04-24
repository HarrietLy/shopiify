package com.harriet.shopiify.order.service;

import com.harriet.shopiify.auth.repository.ShippingAddressRepository;
import com.harriet.shopiify.cart.model.CartItem;
import com.harriet.shopiify.cart.model.CartItemKey;
import com.harriet.shopiify.cart.repository.CartItemRepository;
import com.harriet.shopiify.order.dto.OrderDTO;
import com.harriet.shopiify.order.dto.OrderItemDTO;
import com.harriet.shopiify.order.model.Order;
import com.harriet.shopiify.order.model.OrderItem;
import com.harriet.shopiify.order.model.OrderItemKey;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional
    public Long createOrder(OrderCreateVO vo) {
        Long createdOrderId = saveOrder(vo);
        saveOrderItems(vo);
        deleteCartItems(vo);
        reduceStockByOrderedQty(vo);
        return createdOrderId;
    }

    private Long saveOrder(OrderCreateVO vo) {
        log.info("creating new order");
        if (vo.getOrderItems().isEmpty()){
            throw new UnsupportedOperationException("cannot create new order with no order items");
        }
        for ( OrderItemVO item : vo.getOrderItems()){
            log.info("cart id and product id: {}, {} ",item.getCartId(), item.getProductId());
            Optional<CartItem> cartItem = cartItemRepository.findById(toCartItemKey(item.getCartId(), item.getProductId()));
            Long stock = productRepository.findById(item.getProductId()).get().getStock();
            log.info("stock: {}", stock);
            if (cartItem.get().getQuantity()> stock){
                throw new RuntimeException("order creation cannot proceed, insufficient stock for "+ item.getProductId());
            }
        }
        Order createdOrder =orderRepository.save(toOrderEntity(vo));
        return createdOrder.getId();
    }

    private void saveOrderItems (OrderCreateVO vo) {
        log.info("saving order items to the created order");
        try {
            Long createdOrderId = saveOrder(vo);
            vo.getOrderItems().forEach(item ->
                    orderItemRepository.save(toOrderItemEntity(item, createdOrderId)));
        } catch (Exception e){
            throw new RuntimeException("Exception occured in saveOrderItems, "+e.getMessage());
        }
    }

    private void deleteCartItems (OrderCreateVO vo){
        log.info("removing ordered item from cart");
        vo.getOrderItems().forEach(item -> cartItemRepository.deleteById(toCartItemKey(item.getCartId(),item.getProductId())));
    }

    private CartItemKey toCartItemKey (Long cartId, Long productId){
        CartItemKey cartItemKey = new CartItemKey();
        cartItemKey.setCartId(cartId);
        cartItemKey.setProductId(productId);
        return cartItemKey;
    }

    private void reduceStockByOrderedQty(OrderCreateVO vo) {
        log.info("reducing stock by the ordered quantity");
        List<OrderItemVO> orderItems = vo.getOrderItems();
        for (OrderItemVO item : orderItems) {
            Product product = productRepository.findById(item.getProductId()).get();
            Long currentStock = product.getStock();
            Long orderedQuantity = cartItemRepository.findById(toCartItemKey(item.getCartId(), item.getProductId())).get().getQuantity();
            Long remainingStock = currentStock - orderedQuantity;
            if (remainingStock >= 0) {
                product.setStock(remainingStock);
            } else {
                throw new RuntimeException("insufficient stock for the ordered quantity");
            }
        }
    }

    private Order toOrderEntity (OrderCreateVO vo){
        log.info("mapping vo to order entity");
        Order entity = new Order();
        entity.setUserId(shippingAddressRepository.findById(vo.getShippingAddressId()).get().getUserId());
        entity.setShippingAddress(shippingAddressRepository.findById(vo.getShippingAddressId()).get());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setOrderStatus(orderStatusRepository.findById(1L).get());
        Double totalAmount = 0.0;
        for (OrderItemVO item : vo.getOrderItems()){
            CartItem cartItem = cartItemRepository.findById(toCartItemKey(item.getCartId(), item.getProductId())).get();
            totalAmount = totalAmount + productRepository.findById(item.getProductId()).get().getPrice() * cartItem.getQuantity();
        }
        log.info("total amount: {}", totalAmount);
        entity.setProductSubtotal(totalAmount);
        entity.setShippingFee(vo.getShippingFee());
        return entity;
    }

    private OrderItem toOrderItemEntity (OrderItemVO vo, Long orderId){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(orderRepository.findById(orderId).get());
        log.info("order id to get order to set order to ordertime: {}",orderRepository.findById(orderId).get().getId() );
        Product product =productRepository.findById(vo.getProductId()).get();
        CartItem cartItem = cartItemRepository.findById(toCartItemKey(vo.getCartId(), vo.getProductId())).get();
        orderItem.setPrice(product.getPrice());
        log.info("price captured at sale: {}", product.getPrice());
        orderItem.setProduct(product);
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setId(toOrderItemKey(orderId,product.getId()));
        return orderItem;
    }

    public List<OrderDTO> findAllOrders (){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> toOrderDTO(order)).collect(Collectors.toList());
    }

    private OrderDTO toOrderDTO ( Order order){
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setProductSubtotal(order.getProductSubtotal());
        dto.setShippingFee(order.getShippingFee());
        dto.setShippingAddress(order.getShippingAddress().getShippingAddress());
        List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream().map(item
                ->toOrderItemDTO(item)).collect(Collectors.toList());
        dto.setOrderItems(orderItemDTOs);
        dto.setCreatedDate(order.getCreatedDate());
        dto.setOrderStatus(order.getOrderStatus().getStatus());
        return dto;
    }

    private OrderItemDTO toOrderItemDTO (OrderItem orderItem){
        OrderItemDTO dto = new OrderItemDTO();
        dto.setOrderId(orderItem.getOrder().getId());
        dto.setProductId(orderItem.getProduct().getId());
        dto.setPrice(orderItem.getPrice());
        dto.setQuantity(orderItem.getQuantity());
        return dto;
    }

    private OrderItemKey toOrderItemKey(Long orderId, Long productId){
        OrderItemKey key = new OrderItemKey();
        key.setOrderId(orderId);
        key.setProductId(productId);
        return key;
    }

    public void updateOrderStatus (long orderId, long orderStatusId) {
        Order orderToSave = orderRepository.findById(orderId).get();
        Long currentOrderStatusId = orderToSave.getOrderStatus().getId();
        log.info("currentOrderStatusId{}", currentOrderStatusId);
        if (currentOrderStatusId==3){
            throw new UnsupportedOperationException("Order that is already cancelled, cannot be further updated");
        }
        orderToSave.setOrderStatus(orderStatusRepository.findById(orderStatusId).get());
        orderRepository.save(orderToSave);
    }

    public OrderDTO findOrderById(Long orderId){
        return toOrderDTO(orderRepository.findById(orderId).get());
    }
}
