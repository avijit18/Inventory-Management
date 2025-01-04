package com.projectdepot.ecommerce.Inventory_Service.Services;

import com.projectdepot.ecommerce.Inventory_Service.DTOs.OrderRequestDTO;
import com.projectdepot.ecommerce.Inventory_Service.DTOs.ProductDTO;

import java.util.List;

public interface ProductService {

 List<ProductDTO> getAllProducts();
 ProductDTO getProductById(Long id);
 String addProduct(ProductDTO productDTO);

    Double reduceStocks(OrderRequestDTO orderRequestDTO);

    void restoreStocks(OrderRequestDTO orderRequestDTO);
}

