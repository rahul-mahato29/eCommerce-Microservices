package com.microservices.InventoryService.controllers;

import com.microservices.InventoryService.dto.ProductDto;
import com.microservices.InventoryService.services.ProductService;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping(path = "/fetchOrders")
    public String fetchFromOrderService() {
        ServiceInstance orderService = (ServiceInstance) discoveryClient
                .getInstances("orderService").getFirst();

        return restClient.get()
                .uri(orderService.getUri()+"/api/v1/order/helloOrder")
                .retrieve()
                .body(String.class);
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
}
