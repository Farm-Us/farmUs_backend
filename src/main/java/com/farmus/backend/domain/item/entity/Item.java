package com.farmus.backend.domain.item.entity;

import com.farmus.backend.domain.global.BaseEntity;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import com.farmus.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "items")
public class Item extends BaseEntity {
    private static final int MIN_PRICE = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "producer_id")
    private ProducerProfile producer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private int itemPrice;

    private String unit;
    private double weight;

    private String description;

    private String origin;
    private String expirationDate;
    private String storageMethod;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    private int stockQuantity;

    public Item(Long id, ProducerProfile producer, Category category, String itemName, int itemPrice, String unit, double weight, String description, String origin, String expirationDate, String storageMethod, ItemStatus itemStatus, int stockQuantity) {
        this.id = id;
        this.producer = producer;
        this.category = category;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.unit = unit;
        this.weight = weight;
        this.description = description;
        this.origin = origin;
        this.expirationDate = expirationDate;
        this.storageMethod = storageMethod;
        this.itemStatus = itemStatus;
        this.stockQuantity = stockQuantity;
    }

    public Item(ProducerProfile producer, Category mainCategory, String itemName, int itemPrice, String unit, double weight, String description, String origin, String expirationDate, String storageMethod, ItemStatus itemStatus, int stockQuantity) {
        this(null, producer, mainCategory, itemName, itemPrice, unit, weight, description, origin, expirationDate, storageMethod, itemStatus, stockQuantity);
    }

    public void update(Category category, String itemName, int itemPrice, String unit, double weight, String description, String origin, String expirationDate, String storageMethod, ItemStatus itemStatus, int stockQuantity) {
        this.category = category;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.unit = unit;
        this.weight = weight;
        this.description = description;
        this.origin = origin;
        this.expirationDate = expirationDate;
        this.storageMethod = storageMethod;
        this.itemStatus = itemStatus;
        this.stockQuantity = stockQuantity;
    }

    private void validateName(String name) {
        if(name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
    }

    private void validatePrice(int price) {
        if(price < MIN_PRICE) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    private void validateAll(String name, int price) {
        validateName(name);
        validatePrice(price);
    }

    public void addStockQuantity(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStockQuantity(int quantity) {
        int restStockQuantity = this.stockQuantity - quantity;
        if(restStockQuantity < 0) {
            throw new IllegalArgumentException("need more stock quantity");
        }
        this.stockQuantity = restStockQuantity;
    }


}
