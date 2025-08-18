package com.microservices.OrderService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "OrderService", path = "/orders")
public interface OrderFeignClient {

    @GetMapping(path = "/core/helloOrder")
    String hellOrder();
}
