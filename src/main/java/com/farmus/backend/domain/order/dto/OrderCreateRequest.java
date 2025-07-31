package com.farmus.backend.domain.order.dto;

import java.util.List;

public record OrderCreateRequest(
        Long userId,
        List<OrderItemRequest> orderItems,
        String requestedDeliveryNote,
        AddressRequest address
) {
}
