package com.projectdepot.ecommerce.Inventory_Service.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "Order-Service", path = "/orders")
public interface OrdersFeignClients {

    @GetMapping("/core/helloOrderService")
    String helloOrderServiceUsingOpenFeign();
}
