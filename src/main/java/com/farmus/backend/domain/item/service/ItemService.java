package com.farmus.backend.domain.item.service;

import com.farmus.backend.domain.global.s3.S3Service;
import com.farmus.backend.domain.item.dto.*;
import com.farmus.backend.domain.item.entity.*;
import com.farmus.backend.domain.item.exception.ItemErrorCode;
import com.farmus.backend.domain.item.exception.ItemException;
import com.farmus.backend.domain.item.repository.CategoryRepository;
import com.farmus.backend.domain.item.repository.ItemCategoryRepository;
import com.farmus.backend.domain.item.repository.ItemOptionRepository;
import com.farmus.backend.domain.item.repository.ItemRepository;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import com.farmus.backend.domain.user.repository.ProducerProfileRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final ProducerProfileRepository producerProfileRepository;
    private final CategoryRepository categoryRepository;
    private final S3Service s3Service;

    public ItemDto createItem(Long producerId, ItemCreateRequest request,
                              MultipartFile thumbnailImage,
                              @Nullable MultipartFile originImage,
                              @Nullable MultipartFile farmingImage,
                              @Nullable MultipartFile manageImage,
                              @Nullable MultipartFile packageImage) {
        ProducerProfile producerProfile = producerProfileRepository.findOrThrow(producerId);
        Category mainCategory = categoryRepository.findOrThrow(request.categoryId());

        String thumbnailImageUrl = uploadImageSafely(thumbnailImage);
        String originImageUrl = uploadImageSafely(originImage);
        String farmingImageUrl = uploadImageSafely(farmingImage);
        String manageImageUrl = uploadImageSafely(manageImage);
        String packageImageUrl = uploadImageSafely(packageImage);


        double discountRate = parseDiscountRate(request.discountRate());
        // 2. 할인 가격 계산
        int discountedPrice = calculateDiscountedPrice(request.itemPrice(), discountRate);

        Item item = itemRepository.save(request.toEntity(producerProfile, mainCategory, discountRate, thumbnailImageUrl, originImageUrl, farmingImageUrl, manageImageUrl, packageImageUrl));

        List<Long> subCategoryIds = request.subCategoryIds();
        if (subCategoryIds == null) {
            subCategoryIds = Collections.emptyList();
        }
        validateSubCategoryCounts(subCategoryIds);

        List<ItemCategory> subCategories = new ArrayList<>();
        for (Long subCategoryId : subCategoryIds) {
            Category category = categoryRepository.findOrThrow(subCategoryId);
            ItemCategory itemCategory = ItemCategory.of(item, category);
            itemCategoryRepository.save(itemCategory);
            subCategories.add(itemCategory);
        }

        List<ItemOptionRequest> optionRequests = request.options();
        if (optionRequests == null) optionRequests = Collections.emptyList();
        for (ItemOptionRequest optionReq : optionRequests) {
            ItemOption option = ItemOption.of(
                    item,
                    optionReq.optionName(),
                    optionReq.optionValue(),
                    optionReq.itemPrice()
            );
            itemOptionRepository.save(option);
        }
        return ItemDto.of(item, discountedPrice, subCategories);
    }

    @Transactional(readOnly = true)
    public Page<ItemDto> getAllItems(Pageable pageable) {
        return itemRepository.findAll(pageable)
                .map(item -> ItemDto.of(item, itemCategoryRepository.findByItem(item)));
    }

    @Transactional(readOnly = true)
    public ItemDto getItemById(Long itemId) {
        Item item = itemRepository.findOrThrow(itemId);
        return ItemDto.of(item);
    }

    @Transactional(readOnly = true)
    public Page<ItemDto> getItemsByCategory(Long categoryId, Pageable pageable){
        return itemRepository.findByCategoryId(categoryId, pageable)
                .map(item -> ItemDto.of(item, itemCategoryRepository.findByItem(item)));
    }

    @Transactional(readOnly = true)
    public Page<ItemDto> getItemsByProducer(Long producerId, Pageable pageable){
        return itemRepository.findByProducerId(producerId, pageable)
                .map(item -> ItemDto.of(item, itemCategoryRepository.findByItem(item)));
    }

    @Transactional(readOnly = true)
    public CategoriesDto getCategory() {
        List<Category> categories = categoryRepository.findAll();
        return CategoriesDto.of(categories);
    }

    public ItemDto updateItem(Long itemId, ItemUpdateRequest request) {
        Item item = itemRepository.findOrThrow(itemId);
        Category mainCategory = categoryRepository.findOrThrow(request.categoryId());

        List<Long> subCategoryIds = request.subCategoryIds();
        if (subCategoryIds == null) {
            subCategoryIds = Collections.emptyList();
        }
        validateSubCategoryCounts(subCategoryIds);

        item.update(
                mainCategory,
                request.itemName(),
                request.itemPrice(),
                request.discountRate(),
                request.description(),
                request.origin(),
                request.expirationDate(),
                request.storageMethod(),
                ItemStatus.valueOf(request.itemStatus()),
                request.stockQuantity()
        );

        List<ItemCategory> existing = itemCategoryRepository.findByItem(item);
        itemCategoryRepository.deleteAll(existing);
        List<ItemCategory> subCategories = subCategoryIds.stream()
                .map(subCategoryId -> {
                    Category category = categoryRepository.findOrThrow(subCategoryId);
                    return ItemCategory.of(item, category);
                })
                .distinct()
                .toList();
        itemCategoryRepository.saveAll(subCategories);

        return ItemDto.of(item, subCategories);
    }

    public void deleteItem(Long itemId) {
        Item item = itemRepository.findOrThrow(itemId);
        itemCategoryRepository.deleteAll(itemCategoryRepository.findByItem(item));
        itemRepository.delete(item);
    }

    private void validateSubCategoryCounts(List<Long> subCategoryIds) {
        if (subCategoryIds == null) return;
//        if (subCategoryIds.size() > 3) {
//            throw new ItemException(ItemErrorCode.SUB_CATEGORY_COUNT_LIMIT);
//        }

    }
    private double parseDiscountRate(String discountRate) {
        if (discountRate == null) return 0.0;
        String number = discountRate.replaceAll("[^0-9.]", "");
        if (number.isEmpty()) return 0.0;
        return Double.parseDouble(number);
    }

    private int calculateDiscountedPrice(int itemPrice, double discountRate) {
        return (int) Math.round(itemPrice * (1 - discountRate / 100.0));
    }
    private String uploadImageSafely(MultipartFile image) {
        if (image == null) {
            return null;
        }
        try {
            return s3Service.uploadFile(image);
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.", e);
        }
    }



}
