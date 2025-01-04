package com.projectdepot.ecommerce.Order_Service.Service;

import com.projectdepot.ecommerce.Order_Service.DTOs.OrderRequestDTO;

import java.util.List;

public interface OrderService {

    List<OrderRequestDTO> getAllOrders();

  OrderRequestDTO getOrderById(Long id);

    OrderRequestDTO createOrder(OrderRequestDTO orderRequestDTO);

    String cancelOrder(Long orderId);
}
