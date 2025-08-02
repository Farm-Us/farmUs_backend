package com.farmus.backend.domain.item.dto;

import com.farmus.backend.domain.item.entity.ItemOption;

public record ItemOptionDto(
        Long id,
        String optionName,
        String optionValue,
        int optionPrice
) {
    public static ItemOptionDto of(ItemOption option) {
        return new ItemOptionDto(
                option.getId(),
                option.getOptionName(),
                option.getOptionValue(),
                option.getOptionPrice()
        );
    }
}
