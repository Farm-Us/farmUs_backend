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
        String discountRate,
        String description,
        String origin,
        String originDetails,
        String farmingMethod,
        String farmingDetails,
        String manageMethod,
        String manageDetails,
        String packageMethod,
        String packageDetails,
        String expirationDate,
        String storageMethod,
        String itemStatus,
        int stockQuantity,
        List<Long> subCategoryIds,
        List<ItemOptionRequest> options
) {
    public Item toEntity(ProducerProfile producerProfile, Category category, double discountRate,
                         String thumbnailImageUrl, String originImageUrl,String farmingImageUrl,String manageImageUrl,String packageImageUrl) {
        return new Item(
                producerProfile,
                category,
                thumbnailImageUrl,
                itemName,
                itemPrice,
                discountRate,
                description,
                originImageUrl,
                origin,
                originDetails,
                farmingImageUrl,
                farmingMethod,
                farmingDetails,
                manageImageUrl,
                manageMethod,
                manageDetails,
                packageImageUrl,
                packageMethod,
                packageDetails,
                expirationDate,
                storageMethod,
                ItemStatus.valueOf(itemStatus),
                stockQuantity
        );
    }
}
