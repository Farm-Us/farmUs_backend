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
        String thumbnailImageUrl,
        String itemName,
        int discountedPrice,
        String description,
        String originImageUrl,
        String origin,
        String originDetails,
        String farmingImageUrl,
        String farmingMethod,
        String farmingDetails,
        String manageImageUrl,
        String manageMethod,
        String manageDetails,
        String packageImageUrl,
        String packageMethod,
        String packageDetails,
        String expirationDate,
        String storageMethod,
        ItemStatus itemStatus,
        int stockQuantity,
        List<ItemOptionDto> options
) {
    public static ItemDto of(Item item, int discountedPrice, List<ItemCategory> subCategories) {
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
                item.getThumbnailImageUrl(),
                item.getItemName(),
                discountedPrice,
                item.getDescription(),
                item.getOriginImageUrl(),
                item.getOrigin(),
                item.getOriginDetails(),
                item.getFarmingImageUrl(),
                item.getFarmingMethod(),
                item.getFarmingDetails(),
                item.getManageImageUrl(),
                item.getManageMethod(),
                item.getManageDetails(),
                item.getPackageImageUrl(),
                item.getPackageMethod(),
                item.getPackageDetails(),
                item.getExpirationDate(),
                item.getStorageMethod(),
                item.getItemStatus(),
                item.getStockQuantity(),
                item.getOptions().stream()
                        .map(ItemOptionDto::of)
                        .toList()
        );
    }
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
                item.getThumbnailImageUrl(),
                item.getItemName(),
                item.getItemPrice(),
                item.getDescription(),
                item.getOriginImageUrl(),
                item.getOrigin(),
                item.getOriginDetails(),
                item.getFarmingImageUrl(),
                item.getFarmingMethod(),
                item.getFarmingDetails(),
                item.getManageImageUrl(),
                item.getManageMethod(),
                item.getManageDetails(),
                item.getPackageImageUrl(),
                item.getPackageMethod(),
                item.getPackageDetails(),
                item.getExpirationDate(),
                item.getStorageMethod(),
                item.getItemStatus(),
                item.getStockQuantity(),
                item.getOptions().stream()
                        .map(ItemOptionDto::of)
                        .toList()
        );
    }
    public static ItemDto of(Item item) {
        return new ItemDto(
                item.getId(),
                item.getProducer().getId(),
                item.getCategory().getId(),
                item.getCategory().getCategoryName(),
                null,
                item.getThumbnailImageUrl(),
                item.getItemName(),
                item.getItemPrice(),
                item.getDescription(),
                item.getOriginImageUrl(),
                item.getOrigin(),
                item.getOriginDetails(),
                item.getFarmingImageUrl(),
                item.getFarmingMethod(),
                item.getFarmingDetails(),
                item.getManageImageUrl(),
                item.getManageMethod(),
                item.getManageDetails(),
                item.getPackageImageUrl(),
                item.getPackageMethod(),
                item.getPackageDetails(),
                item.getExpirationDate(),
                item.getStorageMethod(),
                item.getItemStatus(),
                item.getStockQuantity(),
                item.getOptions().stream()
                        .map(ItemOptionDto::of)
                        .toList()
        );
    }
}
