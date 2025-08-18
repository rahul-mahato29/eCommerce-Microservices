package com.microservices.InventoryService.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private List<OrderItemDto> items;
}
