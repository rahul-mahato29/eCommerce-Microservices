package com.microservices.OrderService.dto;

import com.microservices.OrderService.entities.OrderItem;
import com.microservices.OrderService.entities.enums.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private OrderStatus orderStatus;
    private double totalPrice;
    private List<OrderItem> items;
}
