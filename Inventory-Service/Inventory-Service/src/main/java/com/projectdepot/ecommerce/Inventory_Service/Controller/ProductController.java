package com.projectdepot.ecommerce.Inventory_Service.Controller;

import com.projectdepot.ecommerce.Inventory_Service.Clients.OrdersFeignClients;
import com.projectdepot.ecommerce.Inventory_Service.DTOs.OrderRequestDTO;
import com.projectdepot.ecommerce.Inventory_Service.DTOs.ProductDTO;
import com.projectdepot.ecommerce.Inventory_Service.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;


import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Getting the OrderService Details using Discovery Client
    private final DiscoveryClient discoveryClient;

    // 3rd party call using RestClient
    //private final RestClient restClient;

    // Using Open Feign
    private final OrdersFeignClients ordersFeignClients;


    @GetMapping("/getOrders")
    public String getCallFromOrderServices() {
//        ServiceInstance serviceInstance = discoveryClient.getInstances("Order-Service").getFirst();
//
//        return restClient.get()
//                .uri(serviceInstance.getUri()+"/orders/core/helloOrderService")
//                .retrieve()
//                .body(String.class);

        // Using Open Feign clients:
        return ordersFeignClients.helloOrderServiceUsingOpenFeign();
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllInventory() {
        List<ProductDTO> inventories = productService.getAllProducts();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getInventoryById(@PathVariable Long id) {
        ProductDTO inventory = productService.getProductById(id);
        return ResponseEntity.ok(inventory);
    }

    @PostMapping
    public ResponseEntity<String> createInventory(@RequestBody ProductDTO productDTO) {
        String product = productService.addProduct(productDTO);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDTO orderRequestDTO) {
       Double totalPrice = productService.reduceStocks(orderRequestDTO);
       return ResponseEntity.ok(totalPrice);
    }

    @PutMapping("/restore-stocks")
    public ResponseEntity<String> restoreStocks(@RequestBody OrderRequestDTO orderRequestDTO) {
        productService.restoreStocks(orderRequestDTO);
        return ResponseEntity.ok("Stocks restored successfully.");
    }

}

