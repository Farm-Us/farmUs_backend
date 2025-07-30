package com.farmus.backend.domain.item.service;

import com.farmus.backend.domain.item.dto.ItemCreateRequest;
import com.farmus.backend.domain.item.dto.ItemDto;
import com.farmus.backend.domain.item.dto.ItemUpdateRequest;
import com.farmus.backend.domain.item.entity.Category;
import com.farmus.backend.domain.item.entity.Item;
import com.farmus.backend.domain.item.entity.ItemCategory;
import com.farmus.backend.domain.item.entity.ItemStatus;
import com.farmus.backend.domain.item.exception.ItemErrorCode;
import com.farmus.backend.domain.item.exception.ItemException;
import com.farmus.backend.domain.item.repository.CategoryRepository;
import com.farmus.backend.domain.item.repository.ItemCategoryRepository;
import com.farmus.backend.domain.item.repository.ItemRepository;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import com.farmus.backend.domain.user.repository.ProducerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final ProducerProfileRepository producerProfileRepository;
    private final CategoryRepository categoryRepository;

    public ItemDto createItem(Long producerId, ItemCreateRequest request) {
        ProducerProfile producerProfile = producerProfileRepository.findOrThrow(producerId);
        Category mainCategory = categoryRepository.findOrThrow(request.categoryId());
        Item item = itemRepository.save(request.toEntity(producerProfile, mainCategory));

        validateSubCategoryCounts(request.subCategoryIds());

        List<ItemCategory> subCategories = new ArrayList<>();
        for (Long subCategoryId : request.subCategoryIds()){
            Category category = categoryRepository.findOrThrow(subCategoryId);
            ItemCategory itemCategory = ItemCategory.of(item, category);
            itemCategoryRepository.save(itemCategory);
            subCategories.add(itemCategory);
        }
        return ItemDto.of(item, subCategories);
    }

    @Transactional(readOnly = true)
    public Page<ItemDto> getAllItems(Pageable pageable) {
        return itemRepository.findAll(pageable)
                .map(item -> ItemDto.of(item, itemCategoryRepository.findByItem(item)));
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

    public ItemDto updateItem(Long itemId, ItemUpdateRequest request) {
        Item item = itemRepository.findOrThrow(itemId);
        Category mainCategory = categoryRepository.findOrThrow(request.categoryId());

        validateSubCategoryCounts(request.subCategoryIds());

        item.update(
                mainCategory,
                request.itemName(),
                request.itemPrice(),
                request.unit(),
                request.weight(),
                request.description(),
                request.origin(),
                request.expirationDate(),
                request.storageMethod(),
                ItemStatus.valueOf(request.itemStatus()),
                request.stockQuantity()
        );

        List<ItemCategory> existing = itemCategoryRepository.findByItem(item);
        itemCategoryRepository.deleteAll(existing);
        List<ItemCategory> subCategories = request.subCategoryIds().stream()
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
        if (subCategoryIds.size() > 3) {
            throw new ItemException(ItemErrorCode.SUB_CATEGORY_COUNT_LIMIT);
        }
    }


}
