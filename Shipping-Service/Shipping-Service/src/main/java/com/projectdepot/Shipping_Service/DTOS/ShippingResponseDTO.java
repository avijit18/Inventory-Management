package com.projectdepot.Shipping_Service.DTOS;

import com.projectdepot.Shipping_Service.Entity.ENUM.ShippingStatus;
import lombok.Data;

@Data
public class ShippingResponseDTO {
    private Long id;

    private Long orderID;

    private ShippingStatus shippingStatus;

    private String trackingNumber;
}
