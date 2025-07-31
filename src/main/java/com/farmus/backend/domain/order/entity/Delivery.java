package com.farmus.backend.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public Delivery(Long id, Order order, Address address, DeliveryStatus deliveryStatus) {
        this.id = id;
        this.order = order;
        this.address = address;
        this.deliveryStatus = deliveryStatus;
    }

    public Delivery(Address address, DeliveryStatus deliveryStatus) {
        this(null, null, address, deliveryStatus);
    }
}
