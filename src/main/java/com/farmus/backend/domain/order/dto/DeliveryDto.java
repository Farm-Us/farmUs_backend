package com.farmus.backend.domain.order.dto;

import com.farmus.backend.domain.order.entity.Delivery;

public record DeliveryDto(
        String status,
        AddressRequest address
) {
    public static DeliveryDto of(Delivery delivery) {
        return new DeliveryDto(
                delivery.getDeliveryStatus().name(),
                new AddressRequest(
                        delivery.getAddress().getZipCode(),
                        delivery.getAddress().getStreet(),
                        delivery.getAddress().getDetail()
                )
        );
    }
}
