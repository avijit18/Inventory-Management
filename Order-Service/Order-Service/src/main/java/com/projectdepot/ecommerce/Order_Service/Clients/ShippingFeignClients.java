package com.projectdepot.ecommerce.Order_Service.Clients;

import com.projectdepot.Shipping_Service.Entity.Shipping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "Shipping-Service", path = "/shipping")
public interface ShippingFeignClients {

    @PostMapping("/status/confirm/{orderId}")
    Shipping confirmShipping(@PathVariable("orderId") Long orderId);

    @PostMapping("/status/cancel/{orderId}")
    String cancelShipping(@PathVariable("orderId") Long orderId);
}
