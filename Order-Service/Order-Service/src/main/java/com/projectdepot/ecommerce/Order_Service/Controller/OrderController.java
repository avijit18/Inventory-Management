package com.projectdepot.ecommerce.Order_Service.Controller;

import com.projectdepot.ecommerce.Order_Service.Clients.InventoryFeignClients;
import com.projectdepot.ecommerce.Order_Service.DTOs.OrderRequestDTO;
import com.projectdepot.ecommerce.Order_Service.Service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.DoubleBuffer;
import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    // Service Discovery using Dummy API
    @GetMapping("/helloOrderService")
    public String helloOrderService(@RequestHeader("X-user-ID") Long userID) {
        return "Hello from Order Service using Netflix Eureka. user id is" + userID;
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO,
                                                       @RequestHeader("X-user-ID") Long userID) {
       OrderRequestDTO orderRequest =  orderService.createOrder(orderRequestDTO);
       return ResponseEntity.ok(orderRequest);
    }

    @PutMapping("/cancel-order/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        String responseMessage = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDTO>> getAllOrders(HttpServletRequest httpServletRequest) {
        log.info("Fetching all orders via controller");
        List<OrderRequestDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDTO> getOrderById(@PathVariable Long id) {
        log.info("Fetching order with ID: {} via controller", id);
        OrderRequestDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
