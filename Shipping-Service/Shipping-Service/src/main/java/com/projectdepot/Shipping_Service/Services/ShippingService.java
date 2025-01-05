package com.projectdepot.Shipping_Service.Services;

import com.projectdepot.Shipping_Service.DTOS.ShippingRequestDTO;
import com.projectdepot.Shipping_Service.DTOS.ShippingResponseDTO;

public interface ShippingService {

    ShippingResponseDTO confirmShipping(Long orderID);

}
