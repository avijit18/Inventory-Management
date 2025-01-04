package com.projectdepot.ecommerce.Inventory_Service.Repository;

import com.projectdepot.ecommerce.Inventory_Service.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
