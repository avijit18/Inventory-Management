package com.projectdepot.ecommerce.Order_Service.Clients;

import com.projectdepot.ecommerce.Order_Service.DTOs.OrderRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Inventory-Service", path = "/inventory")
public interface InventoryFeignClients {

    @PutMapping("/products/reduce-stocks")
    Double reduceStocks(@RequestBody OrderRequestDTO orderRequestDTO);

    @PutMapping("/products/restore-stocks")
    String restoreStocks(@RequestBody OrderRequestDTO orderRequestDTO);
}
