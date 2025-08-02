package com.farmus.backend.domain.item.dto;

public record ItemOptionRequest(
        String optionName,
        String optionValue,
        int itemPrice
) {
}
