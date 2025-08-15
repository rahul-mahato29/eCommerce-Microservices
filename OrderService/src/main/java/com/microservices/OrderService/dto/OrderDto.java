package com.microservices.OrderService.dto;

import com.microservices.OrderService.entities.OrderItem;
import com.microservices.OrderService.entities.enums.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private OrderStatus orderStatus;
    private double totalPrice;
    private List<OrderItem> items;
}
