package com.projectdepot.ecommerce.Inventory_Service.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private List<OrderRequestItemDTO> items;
}
