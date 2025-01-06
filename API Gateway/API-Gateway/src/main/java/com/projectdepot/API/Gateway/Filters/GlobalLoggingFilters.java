package com.projectdepot.API.Gateway.Filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalLoggingFilters implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // This is for pre filter
        log.info("This is Pre filters call from global Filters + {}", exchange.getRequest().getURI());
        // return chain.filter(exchange);

        // for post filter
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("This is Post filters call from global Filters + {}", exchange.getResponse().getStatusCode());
        }));
    }
}
