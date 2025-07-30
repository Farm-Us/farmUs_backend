package com.farmus.backend.domain.item.dto;

import com.farmus.backend.domain.item.entity.Item;
import com.farmus.backend.domain.item.entity.ItemCategory;
import com.farmus.backend.domain.item.entity.ItemStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ItemDto(
        Long itemId,
        Long producerId,
        Long categoryId,
        String categoryName,
        List<ItemCategoryDto> subCategories,
        String itemName,
        int itemPrice,
        String unit,
        double weight,
        String description,
        String origin,
        String expirationDate,
        String storageMethod,
        ItemStatus itemStatus,
        int stockQuantity
) {
    public static ItemDto of(Item item, List<ItemCategory> subCategories) {
        List<ItemCategoryDto> categories = subCategories.stream()
                .map(ic ->new ItemCategoryDto(
                        ic.getItem().getId(),
                        ic.getCategory().getId(),
                        ic.getCategory().getCategoryName()
                ))
                .toList();
        return new ItemDto(
                item.getId(),
                item.getProducer().getId(),
                item.getCategory().getId(),
                item.getCategory().getCategoryName(),
                categories,
                item.getItemName(),
                item.getItemPrice(),
                item.getUnit(),
                item.getWeight(),
                item.getDescription(),
                item.getOrigin(),
                item.getExpirationDate(),
                item.getStorageMethod(),
                item.getItemStatus(),
                item.getStockQuantity()
        );
    }
}
