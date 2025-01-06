package com.projectdepot.API.Gateway.Filters;

import com.projectdepot.API.Gateway.Service.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JWTService jwtService;

    public AuthenticationFilter(JWTService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String AuthenticationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if(AuthenticationHeader == null){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = AuthenticationHeader.split("Bearer ")[1];
            Long userID = jwtService.getUserIdFromJwtToken(token);
            exchange.getRequest()
                    .mutate()
                    .header("X-user-ID", userID.toString())
                    .build();
            return chain.filter(exchange);
        };
    }

    public static class Config{

    }
}
