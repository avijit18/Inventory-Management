package com.projectdepot.ecommerce.Order_Service.Service.IMPL;

import com.projectdepot.Shipping_Service.Entity.Shipping;
import com.projectdepot.ecommerce.Order_Service.Clients.InventoryFeignClients;
import com.projectdepot.ecommerce.Order_Service.Clients.ShippingFeignClients;
import com.projectdepot.ecommerce.Order_Service.DTOs.OrderRequestDTO;
import com.projectdepot.ecommerce.Order_Service.Entities.Enum.OrderStatus;
import com.projectdepot.ecommerce.Order_Service.Entities.OrderItem;
import com.projectdepot.ecommerce.Order_Service.Entities.Orders;
import com.projectdepot.ecommerce.Order_Service.Repository.OrderRepository;
import com.projectdepot.ecommerce.Order_Service.Service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceIMPL implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    private final InventoryFeignClients inventoryFeignClients;
    private final ShippingFeignClients shippingFeignClients;


    @Override
    public List<OrderRequestDTO> getAllOrders() {
        log.info("Fetching all orders");
        List<Orders> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderRequestDTO.class)).toList();
    }

    @Override
    public OrderRequestDTO getOrderById(Long id) {
        log.info("Fetching order with ID: {}", id);
        Orders order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderRequestDTO.class);
    }

    @Override
//    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
//    @RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "createOrderFallback")
    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "createOrderFallback")
    public OrderRequestDTO createOrder(OrderRequestDTO orderRequestDTO) {
        log.info("Creating order: {}", orderRequestDTO);
        Double totalPrice = inventoryFeignClients.reduceStocks(orderRequestDTO);

        Orders order = modelMapper.map(orderRequestDTO, Orders.class);

        // one person can order multiple products, hence order id will be 1 for one person
        for (OrderItem orderItem : order.getItems()) {
            orderItem.setOrder(order);
        }
        order.setTotalPrice(totalPrice);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        Orders saveOrder = orderRepository.save(order);

        // Confirm shipping via shipping service
        Shipping shipping = shippingFeignClients.confirmShipping(saveOrder.getId());
        log.info("Shipping confirmed: {}", shipping);
        return modelMapper.map(saveOrder, OrderRequestDTO.class);
    }

    public OrderRequestDTO createOrderFallback(OrderRequestDTO orderRequestDTO, Throwable throwable) {
        log.error("Failed to create order: {}", throwable.getMessage());

        // this is dummy data
        return new OrderRequestDTO();
    }

    @Override
    @Transactional
    public String cancelOrder(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order is already cancelled");
        }

        // Call Inventory Service to restore stocks
        inventoryFeignClients.restoreStocks(modelMapper.map(order, OrderRequestDTO.class));

        // Update order status after cancelling
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return "Order with ID: " + orderId + " has been cancelled successfully, and stocks are restored.";
    }


}
