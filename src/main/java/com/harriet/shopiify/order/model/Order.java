package com.harriet.shopiify.order.model;

import com.harriet.shopiify.auth.model.ShippingAddress;
import com.harriet.shopiify.order.dto.OrderDTO;
import com.harriet.shopiify.order.dto.OrderItemDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name="customer_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
    // TODO KIV if wanna associate with User entity so can fetch list of order upon login
    private Long userId;

    private Double productSubtotal;

    private Double shippingFee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id")
    private ShippingAddress shippingAddress;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name="order_status_id")
    private OrderStatus orderStatus;

    public OrderDTO toOrderDTO (){
        OrderDTO dto = new OrderDTO();
        dto.setId(this.id);
        dto.setUserId(this.userId);
        dto.setProductSubtotal(this.productSubtotal);
        dto.setShippingFee(this.shippingFee);
        dto.setShippingAddress(this.shippingAddress.getShippingAddress());
        List<OrderItemDTO> orderItemDTOs = this.orderItems.stream().map(item
                ->item.toOrderItemDTO()).collect(Collectors.toList());
        dto.setOrderItems(orderItemDTOs);
        dto.setCreatedDate(this.createdDate);
        dto.setOrderStatus(this.orderStatus.getStatus());
        return dto;
    }

    public Order (ShippingAddress shippingAddress, LocalDateTime createdDate, OrderStatus orderStatus, Double productSubtotal, Double shippingFee){
        this.userId = shippingAddress.getUserId();
        this.shippingAddress = shippingAddress;
        this.createdDate = createdDate;
        this.orderStatus= orderStatus;
        this.productSubtotal= productSubtotal;
        this.shippingFee = shippingFee;
    }

    public Order(){}

}
