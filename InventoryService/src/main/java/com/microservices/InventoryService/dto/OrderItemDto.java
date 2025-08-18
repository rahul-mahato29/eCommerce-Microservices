package com.microservices.InventoryService.controllers;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long productId;
    private Integer quantity;
}
