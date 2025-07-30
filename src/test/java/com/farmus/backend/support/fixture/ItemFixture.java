package com.farmus.backend.support.fixture;

import com.farmus.backend.domain.item.entity.Category;
import com.farmus.backend.domain.item.entity.Item;
import com.farmus.backend.domain.item.entity.ItemStatus;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public enum ItemFixture {
    백도("백도", 8900, "5개 묶음", 334.0, "딱딱한 과육의 백도입니다.", "경북 영천", "2025-08-08", "실온 보관", ItemStatus.AVAILABLE, 100),
    황도("황도", 8900, "5개 묶음", 336.0, "달콤한 황도입니다.", "경북 영천", "2025-08-08", "실온 보관", ItemStatus.AVAILABLE, 100);

    private String itemName;
    private int itemPrice;
    private String unit;
    private double weight;
    private String description;
    private String origin;
    private String expirationDate;
    private String storageMethod;
    private ItemStatus status;
    private int stockQuantity;

    ItemFixture(String itemName, int itemPrice, String unit, double weight, String description, String origin, String expirationDate, String storageMethod, ItemStatus status, int stockQuantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.unit = unit;
        this.weight = weight;
        this.description = description;
        this.origin = origin;
        this.expirationDate = expirationDate;
        this.storageMethod = storageMethod;
        this.status = status;
        this.stockQuantity = stockQuantity;
    }

    public static Item 백도(ProducerProfile producer, Category category) {
        return new Item(producer, category, 백도.itemName, 백도.itemPrice, 백도.unit, 백도.weight, 백도.description, 백도.origin, 백도.expirationDate, 백도.storageMethod, 백도.status, 백도.stockQuantity);
    }

    public static Item 황도(ProducerProfile producer, Category category) {
        return new Item(producer, category, 황도.itemName, 황도.itemPrice, 황도.unit, 황도.weight, 황도.description, 황도.origin, 황도.expirationDate, 황도.storageMethod, 황도.status, 황도.stockQuantity);
    }








}
