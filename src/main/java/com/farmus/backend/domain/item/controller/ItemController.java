package com.farmus.backend.domain.item.controller;

import com.farmus.backend.domain.global.success.SuccessResponse;
import com.farmus.backend.domain.item.dto.CategoriesDto;
import com.farmus.backend.domain.item.dto.ItemCreateRequest;
import com.farmus.backend.domain.item.dto.ItemDto;
import com.farmus.backend.domain.item.dto.ItemUpdateRequest;
import com.farmus.backend.domain.item.exception.ItemSuccessCode;
import com.farmus.backend.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping(value = "/{producerId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<ItemDto>> createItem(
            @PathVariable Long producerId,
            @RequestPart("request") ItemCreateRequest request,
            @RequestPart(value = "thumbnailImage", required = false) MultipartFile thumbnailImage,
            @RequestPart(value = "originImage", required = false) MultipartFile originImage,
            @RequestPart(value = "farmingImage", required = false) MultipartFile farmingImage,
            @RequestPart(value = "manageImage", required = false) MultipartFile manageImage,
            @RequestPart(value = "packageImage", required = false) MultipartFile packageImage
    ){
        ItemDto item = itemService.createItem(producerId, request, thumbnailImage, originImage, farmingImage, manageImage, packageImage);
        return ResponseEntity.status(ItemSuccessCode.ITEM_CREATED.getStatus())
                .body(SuccessResponse.of(ItemSuccessCode.ITEM_CREATED, item));
    }

    @GetMapping
    public ResponseEntity<Page<ItemDto>> getAllItems(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable
            ){
        return ResponseEntity.ok(itemService.getAllItems(pageable));
    }

    @GetMapping("{itemId}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.getItemById(itemId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ItemDto>> getItemsByCategory(
            @PathVariable Long categoryId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable
    ){
        return ResponseEntity.ok(itemService.getItemsByCategory(categoryId, pageable));
    }

    @GetMapping("/producer/{producerId}")
    public ResponseEntity<Page<ItemDto>> getItemsByProducer(
            @PathVariable Long producerId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(itemService.getItemsByProducer(producerId, pageable));
    }

    @GetMapping("/category")
    public CategoriesDto getCategories() {
        return itemService.getCategory();
    }


    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> updateItem(
            @PathVariable Long itemId,
            @RequestBody ItemUpdateRequest request
    ){
        ItemDto updateItem = itemService.updateItem(itemId, request);
        return ResponseEntity.ok(updateItem);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long itemId
    ){
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }


}
