package com.microservices.OrderService.controllers;

import com.microservices.OrderService.dto.OrderDto;
import com.microservices.OrderService.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/core")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @GetMapping(path = "/helloOrder")
    public String helloOrders() {
        return "Hello from order service";
    }

    @PostMapping(path = "/createOrder")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto orderDto1 = orderService.createOrder(orderDto);
        return ResponseEntity.ok(orderDto1);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        log.info("Fetching all orders via controller");
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        log.info("Fetching Order with Id : {} via controller", id);
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
