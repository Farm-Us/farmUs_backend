package com.farmus.backend.domain.order.entity;

import com.farmus.backend.domain.item.entity.Item;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int count;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "producer_profile_id")
    private ProducerProfile producerProfile;

    public OrderItem(Long id, Order order, Item item, int orderPrice, int count, ProducerProfile producerProfile) {
        this.id = id;
        this.order = order;
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
        this.producerProfile = producerProfile;
    }

    public OrderItem(Order order, Item item, int orderPrice, int count, ProducerProfile producerProfile) {
        this(null, order, item, orderPrice, count, producerProfile);
    }

    public void increaseStock() {
        item.addStockQuantity(count);
    }
    public void decreaseStock() {
        item.removeStockQuantity(count);
    }

    public void cancel() {
        getItem().addStockQuantity(count);
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    protected void setOrder(Order order) {
        this.order = order;
    }

    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.item = item;
        orderItem.orderPrice = orderPrice;
        orderItem.count = count;
        item.removeStockQuantity(count);
        return orderItem;
    }


}
