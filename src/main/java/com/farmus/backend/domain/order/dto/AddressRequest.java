package com.farmus.backend.domain.order.dto;

public record AddressRequest(
        String zipcode,
        String street,
        String detail
) {
}
