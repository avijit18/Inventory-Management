package com.projectdepot.ecommerce.Inventory_Service.Services.IMPL;

import com.projectdepot.ecommerce.Inventory_Service.DTOs.OrderRequestDTO;
import com.projectdepot.ecommerce.Inventory_Service.DTOs.OrderRequestItemDTO;
import com.projectdepot.ecommerce.Inventory_Service.DTOs.ProductDTO;
import com.projectdepot.ecommerce.Inventory_Service.Entities.Product;
import com.projectdepot.ecommerce.Inventory_Service.Repository.ProductRepository;
import com.projectdepot.ecommerce.Inventory_Service.Services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceIMPL implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductDTO> getAllProducts() {
        log.info("Fetching all inventory items");
        List<Product> inventories = productRepository.findAll();
        return inventories.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    @Override
    public ProductDTO getProductById(Long id) {
        log.info("Fetching Product with ID: {}", id);
        Optional<Product> inventory = productRepository.findById(id);
        return inventory.map(item -> modelMapper.map(item, ProductDTO.class))
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    @Override
    public String addProduct(ProductDTO productDTO) {
        log.info("Adding product: {}", productDTO);
        Product createdProduct = modelMapper.map(productDTO, Product.class);

        productRepository.save(createdProduct);
        return "Product has been created successfully..";
    }

    @Override
    @Transactional
    public Double reduceStocks(OrderRequestDTO orderRequestDTO) {
        log.info("Reduce stocks: {}", orderRequestDTO);

        Double totalPrice = 0.0;

        for (OrderRequestItemDTO orderRequestItemDTO : orderRequestDTO.getItems()) {

            // checking the productID and quantity using orderRequestDTO
            Long productId = orderRequestItemDTO.getProductId();
            Integer quantity = orderRequestItemDTO.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

            // checking if the products are not greater than the stocks
            if (product.getStocksOfProduct() < quantity) {
                throw new RuntimeException("Stocks of product is less than quantity");
            }

            // Reducing the stocks
            product.setStocksOfProduct(product.getStocksOfProduct() - quantity);

            // Calculate the total price
            totalPrice += product.getPriceOfProduct() * quantity;
            productRepository.save(product);
        }
        return totalPrice;
    }

    @Override
    @Transactional
    public void restoreStocks(OrderRequestDTO orderRequestDTO) {
        for (OrderRequestItemDTO orderRequestItemDTO : orderRequestDTO.getItems()) {
            Long productId = orderRequestItemDTO.getProductId();
            Integer quantity = orderRequestItemDTO.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

            // Restoring the stocks after canceling the order
            product.setStocksOfProduct(product.getStocksOfProduct() + quantity);
            productRepository.save(product);
        }
    }
}
