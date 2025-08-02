package com.farmus.backend.domain.item.dto;

import com.farmus.backend.domain.item.entity.Category;

import java.util.List;

public record CategoryDto(
        Long id,
        String categoryName
) {
    public static CategoryDto of(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getCategoryName()
        );
    }
}
