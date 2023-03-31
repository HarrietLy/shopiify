package com.harriet.shopiify.order.controller;

import com.harriet.shopiify.cart.dto.CartDTO;
import com.harriet.shopiify.order.service.OrderService;
import com.harriet.shopiify.order.vo.OrderCreateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Long createOrder(@Valid @RequestBody OrderCreateVO vo){
        return orderService.createOrder(vo);
    }
}
