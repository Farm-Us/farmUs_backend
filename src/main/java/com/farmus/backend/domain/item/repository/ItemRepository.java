package com.farmus.backend.domain.item.repository;

import com.farmus.backend.domain.item.entity.Item;
import com.farmus.backend.domain.item.exception.ItemErrorCode;
import com.farmus.backend.domain.item.exception.ItemException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    default Item findOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new ItemException(ItemErrorCode.ITEM_NOT_FOUND));
    }
    Page<Item> findAll(Pageable pageable);
    Page<Item> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Item> findByProducerId(Long producerId, Pageable pageable);
}
