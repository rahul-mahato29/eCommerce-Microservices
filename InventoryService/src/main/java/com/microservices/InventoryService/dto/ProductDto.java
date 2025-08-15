package com.microservices.InventoryService.dto;

import lombok.Data;

@Data
public class ProductDto {

    private Long id;
    private String title;
    private double price;
    private Integer stock;
}
