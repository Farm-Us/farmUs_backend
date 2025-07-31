package com.farmus.backend.domain.order.dto;

public record OrderItemRequest(
        Long itemId,
        Long producerProfileId,
        int count
) {
}
