package com.farmus.backend.support.fixture;

import com.farmus.backend.domain.order.entity.Address;
import com.farmus.backend.domain.order.entity.DeliveryStatus;
import lombok.Getter;

@Getter
public enum AddressFixture {

    주소("03045", "서울 종로구 사직로 161 경복궁", "경복궁 광장");

    private String zipCode;
    private String street;
    private String detail;

    AddressFixture(String zipCode, String street, String detail) {
        this.zipCode = zipCode;
        this.street = street;
        this.detail = detail;
    }

    public static Address 주소() {
        return new Address(주소.zipCode, 주소.street, 주소.detail);
    }
}
