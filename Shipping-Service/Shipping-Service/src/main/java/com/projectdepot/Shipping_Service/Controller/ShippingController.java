package com.projectdepot.Shipping_Service.Controller;

import com.projectdepot.Shipping_Service.DTOS.ShippingResponseDTO;
import com.projectdepot.Shipping_Service.Services.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @PostMapping("/confirm/{orderId}")
    public ResponseEntity<ShippingResponseDTO> confirmShipping(@PathVariable Long orderId) {
        ShippingResponseDTO responseDTO = shippingService.confirmShipping(orderId);
        return ResponseEntity.ok(responseDTO);
    }
}
