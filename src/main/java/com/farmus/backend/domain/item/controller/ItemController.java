package com.farmus.backend.domain.item.controller;

import com.farmus.backend.domain.global.success.SuccessResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/{producerId}")
    public ResponseEntity<SuccessResponse<ItemDto>> createItem(
            @PathVariable Long producerId,
            @RequestBody ItemCreateRequest request
    ){
        ItemDto item = itemService.createItem(producerId, request);
        return ResponseEntity.status(ItemSuccessCode.ITEM_CREATED.getStatus())
                .body(SuccessResponse.of(ItemSuccessCode.ITEM_CREATED, item));
    }

    @GetMapping
    public ResponseEntity<Page<ItemDto>> getAllItems(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable
            ){
        return ResponseEntity.ok(itemService.getAllItems(pageable));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Page<ItemDto>> getItemsByCategory(
            @PathVariable Long categoryId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable
    ){
        return ResponseEntity.ok(itemService.getItemsByCategory(categoryId, pageable));
    }

    @GetMapping("/{producerId}")
    public ResponseEntity<Page<ItemDto>> getItemsByProducer(
            @PathVariable Long producerId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(itemService.getItemsByProducer(producerId, pageable));
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
