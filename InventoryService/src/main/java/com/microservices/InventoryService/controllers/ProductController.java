package com.microservices.InventoryService.controllers;

import com.microservices.InventoryService.clients.OrderFeignClient;
import com.microservices.InventoryService.dto.OrderDto;
import com.microservices.InventoryService.dto.ProductDto;
import com.microservices.InventoryService.services.ProductService;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.aspectj.asm.IModelFilter;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    private final OrderFeignClient orderFeignClient;

    //Just Testing : Communication between 2 services (Order & Inventory)
    @GetMapping(path = "/fetchOrders")
    public String fetchFromOrderService() {
        return orderFeignClient.hellOrder();
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllInventory() {
        List<ProductDto> inventories = productService.getAllInventory();
        return ResponseEntity.of(Optional.ofNullable(inventories));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDto> getInventoryById(@PathVariable Long id) {
        ProductDto inventory = productService.getProductById(id);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping(path = "reduceStocks")
    private ResponseEntity<Double> reduceStocks(@RequestBody OrderDto orderDto) {
        Double totalPrice = productService.reduceStocks(orderDto);
        return ResponseEntity.ok(totalPrice);
    }
}
