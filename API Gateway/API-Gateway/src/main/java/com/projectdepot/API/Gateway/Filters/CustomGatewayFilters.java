package com.projectdepot.API.Gateway.Filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomGatewayFilters extends AbstractGatewayFilterFactory<CustomGatewayFilters.Config> {


    // Constructor
    public CustomGatewayFilters() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {

            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                log.info("CustomGatewayFilters pre filter + {}", exchange.getRequest().getPath());
                return chain.filter(exchange);

            }
        };
    }

    public static class Config{

    }
}
