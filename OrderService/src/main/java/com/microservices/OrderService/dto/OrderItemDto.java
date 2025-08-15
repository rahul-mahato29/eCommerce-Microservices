package com.microservices.OrderService.dto;

import com.microservices.OrderService.entities.Order;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private Long productId;
    private Integer quantity;
}
