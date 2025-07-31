package com.farmus.backend.domain.order.entity;

import com.farmus.backend.domain.global.BaseEntity;
import com.farmus.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String requestedDeliveryNote;

    public Order(Long id, User user, List<OrderItem> orderItems, Delivery delivery, LocalDateTime orderDate, OrderStatus orderStatus, String requestedDeliveryNote) {
        this.id = id;
        this.user = user;
        this.orderItems = orderItems;
        this.delivery = delivery;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.requestedDeliveryNote = requestedDeliveryNote;
    }

    public Order(User user, List<OrderItem> orderItems, Delivery delivery, LocalDateTime orderDate, OrderStatus orderStatus, String requestedDeliveryNote) {
        this(null, user, orderItems, delivery, orderDate, orderStatus, requestedDeliveryNote);
    }

    public static Order createOrder(User user, Delivery delivery, List<OrderItem> orderItems, String requestedDeliveryNote) {
        Order order = new Order();
        order.user = user;
        order.delivery = delivery;
        order.orderDate = LocalDateTime.now();
        order.orderStatus = OrderStatus.ORDER;
        order.requestedDeliveryNote = requestedDeliveryNote;
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }

    public void cancel(){
        if(delivery.getDeliveryStatus() == DeliveryStatus.COMPLETED || delivery.getDeliveryStatus() == DeliveryStatus.CANCELLED){
            throw new IllegalArgumentException("이미 배송 완료된 상품은 취소 불가능합니다!");
        }
        this.orderStatus = OrderStatus.CANCEL;
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }


}
