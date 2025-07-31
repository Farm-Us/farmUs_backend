package com.farmus.backend.support.fixture;

import com.farmus.backend.domain.item.entity.Item;
import com.farmus.backend.domain.order.entity.Order;
import com.farmus.backend.domain.order.entity.OrderItem;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import lombok.Getter;

@Getter
public enum OrderItemFixture {

    주문상품1(8900, 10),
    주문상품2(8900, 5);


    private int orderPrice;
    private int count;

    OrderItemFixture(int orderPrice, int count) {
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public static OrderItem 주문상품1(Item item) {
        return OrderItem.createOrderItem(item, 주문상품1.orderPrice, 주문상품1.count);
    }

    public static OrderItem 주문상품2(Item item) {
        return OrderItem.createOrderItem(item, 주문상품2.orderPrice, 주문상품2.count);
    }


}
