package com.microservices.InventoryService.services;

import com.microservices.InventoryService.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllInventory();

    ProductDto getProductById(Long id);
}
