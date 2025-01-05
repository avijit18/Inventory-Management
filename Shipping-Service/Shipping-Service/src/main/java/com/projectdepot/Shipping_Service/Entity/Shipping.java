package com.projectdepot.Shipping_Service.Entity;


import com.projectdepot.Shipping_Service.Entity.ENUM.ShippingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderID;

    private ShippingStatus shippingStatus;

    private String trackingNumber;
}
