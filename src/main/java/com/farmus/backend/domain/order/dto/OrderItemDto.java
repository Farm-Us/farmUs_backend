package com.farmus.backend.domain.order.dto;

import com.farmus.backend.domain.order.entity.OrderItem;

public record OrderItemDto(
        Long orderItemId,
        Long itemId,
        String itemName,
        Long producerProfileId,
        String producerName,
        int orderPrice,
        int count
) {
    public static OrderItemDto of(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getId(),
                orderItem.getItem().getId(),
                orderItem.getItem().getItemName(),
                orderItem.getItem().getProducer().getId(),
                orderItem.getItem().getProducer().getFarmName(),
                orderItem.getOrderPrice(),
                orderItem.getCount()
        );
    }
}
