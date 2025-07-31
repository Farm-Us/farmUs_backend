package com.farmus.backend.support.fixture;

import com.farmus.backend.domain.order.entity.Address;
import com.farmus.backend.domain.order.entity.Delivery;
import com.farmus.backend.domain.order.entity.DeliveryStatus;
import lombok.Getter;

@Getter
public enum DeliveryFixture {
    배송지(AddressFixture.주소(), DeliveryStatus.READY);

    private Address address;
    private DeliveryStatus status;

    DeliveryFixture(Address address, DeliveryStatus status) {
        this.address = address;
        this.status = status;
    }

    public static Delivery 배송지() {
        return new Delivery(배송지.address, 배송지.status);
    }


}
