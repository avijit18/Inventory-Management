package com.projectdepot.ecommerce.Order_Service.DTOs;

import lombok.Data;

@Data
public class OrderRequestItemDTO {

    private Long id;
    private Long productId;
    private Integer quantity;
}
