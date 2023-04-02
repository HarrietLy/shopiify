package com.harriet.shopiify.order.controller;

import com.harriet.shopiify.cart.dto.CartDTO;
import com.harriet.shopiify.order.dto.OrderDTO;
import com.harriet.shopiify.order.service.OrderService;
import com.harriet.shopiify.order.vo.OrderCreateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Long createOrder(@Valid @RequestBody OrderCreateVO vo) throws Exception{
        try{
            return orderService.createOrder(vo);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping
    public List<OrderDTO> findAllOrder() {
        return orderService.findAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDTO findOrderById(@PathVariable("orderId") Long orderId){
        return orderService.findOrderById(orderId);
    }

    @PutMapping("/{orderId}")
    public void updateOrderStatus(@PathVariable("orderId") long orderId, @RequestParam("orderStatusId") Long orderStatusId) throws Exception{
        try{
            orderService.updateOrderStatus(orderId, orderStatusId);
        } catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
