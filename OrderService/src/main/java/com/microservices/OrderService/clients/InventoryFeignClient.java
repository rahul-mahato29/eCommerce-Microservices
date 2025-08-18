package com.microservices.OrderService.clients;

import com.microservices.OrderService.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "InventoryService", path = "/inventory")
public interface InventoryFeignClient {

    @PutMapping(path = "/products/reduceStocks")
    Double reduceStocks(@RequestBody OrderDto orderDto);
}
