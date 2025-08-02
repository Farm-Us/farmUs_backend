package com.farmus.backend.domain.item.dto;

import com.farmus.backend.domain.item.entity.Category;

import java.util.List;

public record CategoriesDto(
        List<CategoryDto> categories
) {
    public static CategoriesDto of(List<Category> categoryList) {
        return new CategoriesDto(
                categoryList.stream().map(CategoryDto::of).toList()
        );
    }
}
