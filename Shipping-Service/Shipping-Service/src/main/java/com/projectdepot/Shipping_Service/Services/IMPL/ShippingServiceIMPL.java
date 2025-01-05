package com.projectdepot.Shipping_Service.Services.IMPL;

import com.projectdepot.Shipping_Service.DTOS.ShippingRequestDTO;
import com.projectdepot.Shipping_Service.DTOS.ShippingResponseDTO;
import com.projectdepot.Shipping_Service.Entity.ENUM.ShippingStatus;
import com.projectdepot.Shipping_Service.Entity.Shipping;
import com.projectdepot.Shipping_Service.Repository.ShippingRepository;
import com.projectdepot.Shipping_Service.Services.ShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShippingServiceIMPL implements ShippingService {

    private final ShippingRepository shippingRepository;
    private final ModelMapper modelMapper;


    @Override
    public ShippingResponseDTO confirmShipping(Long orderID) {
        log.info("Confirming shipping for order: {}", orderID);

        // Create a new Shipping entity and set initial values
        Shipping shipping = new Shipping();
        shipping.setOrderID(orderID);
        shipping.setShippingStatus(ShippingStatus.PENDING);
        shipping.setTrackingNumber(UUID.randomUUID().toString());

        // Save the initial shipping entity
        Shipping savedShipping = shippingRepository.save(shipping);

        // Simulate shipping status update to SHIPPED
        savedShipping.setShippingStatus(ShippingStatus.SHIPPED);
        Shipping updatedShipping = shippingRepository.save(savedShipping);

        // Map Shipping entity to ShippingResponseDTO before returning
        return modelMapper.map(updatedShipping, ShippingResponseDTO.class);
    }


    private Shipping mapToEntity(ShippingRequestDTO shippingRequestDTO) {
        return modelMapper.map(shippingRequestDTO, Shipping.class);
    }


    private ShippingResponseDTO mapToDTO(Shipping shipping) {
        return modelMapper.map(shipping, ShippingResponseDTO.class);
    }
}

