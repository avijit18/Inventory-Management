package com.projectdepot.Shipping_Service.Repository;

import com.projectdepot.Shipping_Service.Entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {
}
