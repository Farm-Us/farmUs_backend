package com.farmus.backend.support.fixture;

import com.farmus.backend.domain.order.entity.Delivery;
import com.farmus.backend.domain.order.entity.Order;
import com.farmus.backend.domain.order.entity.OrderItem;
import com.farmus.backend.domain.order.entity.OrderStatus;
import com.farmus.backend.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public enum OrderFixture {
    주문(LocalDateTime.now(), OrderStatus.ORDER, "문앞에 놔둬주세요.");

    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private String requestedDeliveryNote;

    OrderFixture(LocalDateTime orderDate, OrderStatus orderStatus, String requestedDeliveryNote) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.requestedDeliveryNote = requestedDeliveryNote;
    }

    public static Order 주문(User user, List<OrderItem> orderItems, Delivery delivery) {
        return new Order(user, orderItems, delivery, 주문.orderDate, 주문.orderStatus, 주문.requestedDeliveryNote);
    }





}
