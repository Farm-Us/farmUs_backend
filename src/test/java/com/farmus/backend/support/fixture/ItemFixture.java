package com.farmus.backend.support.fixture;

import com.farmus.backend.domain.item.entity.Category;
import com.farmus.backend.domain.item.entity.Item;
import com.farmus.backend.domain.item.entity.ItemStatus;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public enum ItemFixture {
    백도("d", "백도", 8900,  5.0,"딱딱한 과육의 백도입니다.", "sd","경북 영천", "ㅇㅇ","d","d","d","d","d","dd","dd","dd","dd","2025-08-08", "실온 보관", ItemStatus.AVAILABLE, 100),
    황도("d","황도", 8900,  5.0, "달콤한 황도입니다.","d", "경북 영천","ㅇㅇ","d","d","d","d","dd","dd","d ","dd","dd", "2025-08-08", "실온 보관", ItemStatus.AVAILABLE, 100);

    private String thumbnailImageUrl;
    private String itemName;
    private int itemPrice;
    private double discountRate;
    private String description;

    private String originImageUrl;
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
    private ItemStatus status;
    private int stockQuantity;

    ItemFixture(String thumbnailImageUrl, String itemName, int itemPrice, double discountRate, String description,
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
                String packageDetails,String expirationDate, String storageMethod, ItemStatus status, int stockQuantity) {
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
        this.status = status;
        this.stockQuantity = stockQuantity;
    }

    public static Item 백도(ProducerProfile producer, Category category) {
        return new Item(producer, category, 백도.thumbnailImageUrl, 백도.itemName, 백도.itemPrice, 백도.discountRate, 백도.description,
                백도.originImageUrl, 백도.origin, 백도.originDetails, 백도.farmingImageUrl, 백도.farmingMethod, 백도.farmingDetails, 백도.manageImageUrl, 백도.manageMethod, 백도.manageDetails, 백도.packageImageUrl, 백도.packageMethod, 백도.packageDetails, 백도.expirationDate, 백도.storageMethod, 백도.status, 백도.stockQuantity);
    }

    public static Item 황도(ProducerProfile producer, Category category) {
        return new Item(producer, category, 황도.thumbnailImageUrl, 황도.itemName, 황도.itemPrice, 황도.discountRate, 황도.description,
                황도.originImageUrl, 황도.origin, 황도.originDetails, 황도.farmingImageUrl, 황도.farmingMethod, 황도.farmingDetails, 황도.manageImageUrl, 황도.manageMethod, 황도.manageDetails, 황도.packageImageUrl, 황도.packageMethod, 황도.packageDetails, 황도.expirationDate, 황도.storageMethod, 황도.status, 황도.stockQuantity);
    }








}
