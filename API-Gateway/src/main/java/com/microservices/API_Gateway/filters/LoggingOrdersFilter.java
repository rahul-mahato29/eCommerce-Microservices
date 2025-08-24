package com.microservices.API_Gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingOrdersFilter extends AbstractGatewayFilterFactory<LoggingOrdersFilter.config> {

    public LoggingOrdersFilter() {
        super(config.class);
    }

    @Override
    public GatewayFilter apply(config config) {
        return (exchange, chain) -> {
            log.info("Order Filter Pre : {}", exchange.getRequest().getURI());
            return chain.filter(exchange);
        };
    }

    public static class config {

    }
}
