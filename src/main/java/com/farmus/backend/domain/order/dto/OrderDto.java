package com.farmus.backend.domain.order.dto;

import com.farmus.backend.domain.order.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long orderId,
        Long userId,
        List<OrderItemDto> orderItems,
        int totalPrice,
        String status,
        String requestedDeliveryNote,
        LocalDateTime orderDate,
        DeliveryDto delivery
) {
    public static OrderDto of(Order order) {
        return new OrderDto(
                order.getId(),
                order.getUser().getId(),
                order.getOrderItems().stream()
                        .map(OrderItemDto::of)
                        .toList(),
                order.getTotalPrice(),
                order.getOrderStatus().name(),
                order.getRequestedDeliveryNote(),
                order.getOrderDate(),
                DeliveryDto.of(order.getDelivery())
        );
    }
}
