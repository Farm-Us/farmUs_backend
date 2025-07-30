package com.farmus.backend.domain.item.dto;

import com.farmus.backend.domain.item.entity.Category;
import com.farmus.backend.domain.item.entity.Item;
import com.farmus.backend.domain.item.entity.ItemStatus;
import com.farmus.backend.domain.user.entity.ProducerProfile;

import java.time.LocalDateTime;
import java.util.List;

public record ItemCreateRequest(
        Long producerId,
        Long categoryId,
        String itemName,
        int itemPrice,
        String unit,
        double weight,
        String description,
        String origin,
        String expirationDate,
        String storageMethod,
        String itemStatus,
        int stockQuantity,
        List<Long> subCategoryIds
) {
    public Item toEntity(ProducerProfile producerProfile, Category category) {
        return new Item(
                producerProfile,
                category,
                itemName,
                itemPrice,
                unit,
                weight,
                description,
                origin,
                expirationDate,
                storageMethod,
                ItemStatus.valueOf(itemStatus),
                stockQuantity
        );
    }
}
