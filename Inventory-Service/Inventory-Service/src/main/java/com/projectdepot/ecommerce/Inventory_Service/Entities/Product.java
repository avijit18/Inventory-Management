package com.projectdepot.ecommerce.Inventory_Service.Entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameOfProduct;
    private Double priceOfProduct;
    private Integer stocksOfProduct;
}
