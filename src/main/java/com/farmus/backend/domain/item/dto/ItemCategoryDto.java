package com.farmus.backend.domain.item.dto;

public record ItemCategoryDto(
        Long itemId,
        Long categoryId,
        String categoryName
) {
}
