package com.farmus.backend.domain.item.entity;

import com.farmus.backend.domain.global.BaseEntity;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import com.farmus.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private String thumbnailImageUrl;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private int itemPrice;

    private double discountRate;

    private String description;


    private String originImageUrl;    // 재배 환경 이미지
    private String origin;
    private String originDetails;

    private String farmingImageUrl;
    private String farmingMethod;
    private String farmingDetails;

    private String manageImageUrl;
    private String manageMethod;
    private String manageDetails;

    private String packageImageUrl;
    private String packageMethod;
    private String packageDetails;

    private String expirationDate;
    private String storageMethod;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOption> options = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    private int stockQuantity;

    public Item(Long id, ProducerProfile producer, Category category, String thumbnailImageUrl, String itemName, int itemPrice, double discountRate, String description,
                String originImageUrl, String origin, String originDetails, String farmingImageUrl, String farmingMethod, String farmingDetails,
                String manageImageUrl, String manageMethod, String manageDetails, String packageImageUrl, String packageMethod, String packageDetails,
                String expirationDate, String storageMethod, ItemStatus itemStatus, int stockQuantity) {
        this.id = id;
        this.producer = producer;
        this.category = category;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discountRate = discountRate;
        this.description = description;
        this.originImageUrl = originImageUrl;
        this.origin = origin;
        this.originDetails = originDetails;
        this.farmingImageUrl = farmingImageUrl;
        this.farmingMethod = farmingMethod;
        this.farmingDetails = farmingDetails;
        this.manageImageUrl = manageImageUrl;
        this.manageMethod = manageMethod;
        this.manageDetails = manageDetails;
        this.packageImageUrl = packageImageUrl;
        this.packageMethod = packageMethod;
        this.packageDetails = packageDetails;
        this.expirationDate = expirationDate;
        this.storageMethod = storageMethod;
        this.itemStatus = itemStatus;
        this.stockQuantity = stockQuantity;
    }

    public Item(ProducerProfile producer, Category mainCategory, String thumbnailImageUrl, String itemName, int itemPrice, double discountRate, String description,
                String originImageUrl, String origin, String originDetails, String farmingImageUrl, String farmingMethod, String farmingDetails,
                String manageImageUrl, String manageMethod, String manageDetails, String packageImageUrl, String packageMethod, String packageDetails,
                String expirationDate, String storageMethod, ItemStatus itemStatus, int stockQuantity) {
        this(null, producer, mainCategory, thumbnailImageUrl, itemName, itemPrice, discountRate, description,
                originImageUrl, origin, originDetails, farmingImageUrl, farmingMethod, farmingDetails,
                manageImageUrl, manageMethod, manageDetails, packageImageUrl, packageMethod, packageDetails, expirationDate, storageMethod, itemStatus, stockQuantity);
    }

    public void update(Category category, String itemName, int itemPrice, double discountRate, String description, String origin, String expirationDate, String storageMethod, ItemStatus itemStatus, int stockQuantity) {
        this.category = category;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discountRate = discountRate;
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
