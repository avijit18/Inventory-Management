package com.projectdepot.ecommerce.Order_Service.Repository;

import com.projectdepot.ecommerce.Order_Service.Entities.Orders;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
}
