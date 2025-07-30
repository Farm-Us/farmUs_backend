package com.farmus.backend.domain.item.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ItemUpdateRequest(
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
}
