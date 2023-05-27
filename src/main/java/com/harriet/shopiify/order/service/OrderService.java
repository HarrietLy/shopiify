package com.harriet.shopiify.order.service;

import com.harriet.shopiify.auth.model.ShippingAddress;
import com.harriet.shopiify.auth.repository.ShippingAddressRepository;
import com.harriet.shopiify.cart.model.CartItem;
import com.harriet.shopiify.cart.service.CartService;
import com.harriet.shopiify.order.dto.OrderDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final ShippingAddressRepository shippingAddressRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;

    private final CartService cartService;

    @Transactional
    public Long createOrder(OrderCreateVO vo) {
        log.info("OrderCreateVO: {}", vo);
        Long createdOrderId = saveOrder(vo);
        saveOrderItems(vo.getOrderItems(),createdOrderId);
        reduceStockByOrderedQty(vo);
        deleteCartItems(vo);
        return createdOrderId;
    }

    private Long saveOrder(OrderCreateVO vo) {
        log.info("creating new order");
        if (vo.getOrderItems().isEmpty()){
            throw new IllegalArgumentException("cannot create new order with no order items");
        }
        Double productSubtotal = 0.0;
        ShippingAddress shippingAddress = shippingAddressRepository.findById(vo.getShippingAddressId()).get();

        for ( OrderItemVO item : vo.getOrderItems()){
            log.info("cart id and product id: {}, {} ",item.getCartId(), item.getProductId());
            CartItem cartItem = cartService.getCartItemById(item.getCartId(), item.getProductId());;
            Product product = productRepository.findById(item.getProductId()).get();
            productSubtotal = productSubtotal + product.getPrice() * cartItem.getQuantity();

            log.info("checking if this is enough stock for quantity in cart");
            log.info("stock: {}", product.getStock());
            if (product.getProductStatus().getId()==2){
                throw new RuntimeException("cannot add an inactive product to order");
            }
            if (cartItem.getQuantity()> product.getStock()){
                throw new RuntimeException("order creation cannot proceed, insufficient stock for "+ item.getProductId());
            }

            log.info("checking if userid in cart is the same as userid tagged in shippingAddress");
            if (!shippingAddress.getUserId().equals(cartItem.getCart().getUserId())){
                throw new IllegalArgumentException("userid in cart is not same as userid tagged in shippingAddress");
            }
        }

        Order createdOrder = orderRepository.save(new Order(
                shippingAddress
                , LocalDateTime.now()
                , orderStatusRepository.findById(1L).get()
                , productSubtotal
                , vo.getShippingFee()
        ));
        return createdOrder.getId();
    }

    private void saveOrderItems (List<OrderItemVO> orderItems, Long createdOrderId) {
        log.info("saving order items to the created order");
        orderItems.forEach(item ->
                orderItemRepository.save( new OrderItem(
                        orderRepository.findById(createdOrderId).get()
                        , productRepository.findById(item.getProductId()).get()
                        , cartService.getCartItemById(item.getCartId(), item.getProductId())
                        )
                )
        );
    }

    private void deleteCartItems (OrderCreateVO vo){
        log.info("removing ordered item from cart");
        vo.getOrderItems().forEach(item -> cartService.deleteCartItem( item.getCartId(),item.getProductId()));
    }

    private void reduceStockByOrderedQty(OrderCreateVO vo) {
        log.info("reducing stock by the ordered quantity");
        List<OrderItemVO> orderItems = vo.getOrderItems();
        for (OrderItemVO item : orderItems) {
            Product product = productRepository.findById(item.getProductId()).get();
            log.info("product: {}", product);
            Long currentStock = product.getStock();
            log.info("currentStock: {}", currentStock);
            CartItem cartItem = cartService.getCartItemById(item.getCartId(), item.getProductId());
            Long orderedQuantity = cartItem.getQuantity();
            log.info("orderedQuantity: {}", orderedQuantity);
            Long remainingStock = currentStock - orderedQuantity;
            log.info("remainingStock: {}", remainingStock);
            if (remainingStock >= 0) {
                log.info("setting new stock for ordered product");
                product.setStock(remainingStock);
                productRepository.save(product);
            } else {
                throw new RuntimeException("insufficient stock for the ordered quantity");
            }
        }
    }

    public List<OrderDTO> findAllOrders (){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> order.toOrderDTO()).collect(Collectors.toList());
    }

    public void updateOrderStatus (long orderId, long orderStatusId) {
        Order orderToSave = orderRepository.findById(orderId).get();
        Long currentOrderStatusId = orderToSave.getOrderStatus().getId();
        log.info("currentOrderStatusId{}", currentOrderStatusId);
        if (currentOrderStatusId==3){
            throw new IllegalArgumentException("Order that is already cancelled, cannot be further updated");
        }
        orderToSave.setOrderStatus(orderStatusRepository.findById(orderStatusId).get());
        orderRepository.save(orderToSave);
    }

    public OrderDTO findOrderById(Long orderId){
        return orderRepository.findById(orderId).get().toOrderDTO();
    }
}
