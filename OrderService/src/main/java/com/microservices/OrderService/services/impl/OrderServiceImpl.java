package com.microservices.OrderService.services.impl;

import com.microservices.OrderService.clients.InventoryFeignClient;
import com.microservices.OrderService.dto.OrderDto;
import com.microservices.OrderService.entities.Order;
import com.microservices.OrderService.entities.OrderItem;
import com.microservices.OrderService.entities.enums.OrderStatus;
import com.microservices.OrderService.repositories.OrderRepository;
import com.microservices.OrderService.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryFeignClient inventoryFeignClient;

    @Override
    public List<OrderDto> getAllOrders() {
        log.info("Fetching All Orders");
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).toList();
    }

    @Override
    public OrderDto getOrderById(Long id) {
        log.info("Fetching order with Id : {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order Not Found"));
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
//    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
    @RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "createOrderFallback")
    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "createOrderFallback")
    public OrderDto createOrder(OrderDto orderDto) {
        System.out.println("checking");
        Double totalPrice = inventoryFeignClient.reduceStocks(orderDto);

        Order orders = modelMapper.map(orderDto, Order.class);
        for(OrderItem orderItem : orders.getItems()) {
            orderItem.setOrder(orders);
        }
        orders.setTotalPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.CONFIRMED);

        Order savedOrder = orderRepository.save(orders);

        return modelMapper.map(savedOrder, OrderDto.class);
    }

    public OrderDto createOrderFallback(OrderDto orderDto, Throwable throwable) {
        log.error("Fallback occurred due to : {}", throwable.getMessage());
        return new OrderDto();
    }

}
