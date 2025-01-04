package com.projectdepot.ecommerce.Inventory_Service.DTOs;

import lombok.Data;

@Data
public class OrderRequestItemDTO {
    private Long productId;
    private Integer quantity;
}
