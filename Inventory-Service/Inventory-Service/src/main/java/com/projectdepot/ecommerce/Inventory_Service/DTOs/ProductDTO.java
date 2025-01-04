package com.projectdepot.ecommerce.Inventory_Service.DTOs;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id;

    private String nameOfProduct;
    private Double priceOfProduct;
    private Integer stocksOfProduct;
}
