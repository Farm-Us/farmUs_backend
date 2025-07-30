package com.farmus.backend.domain.item.repository;

import com.farmus.backend.domain.item.entity.Category;
import com.farmus.backend.domain.item.entity.Item;
import com.farmus.backend.domain.item.entity.ItemCategory;
import com.farmus.backend.domain.item.exception.ItemErrorCode;
import com.farmus.backend.domain.item.exception.ItemException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
    List<ItemCategory> findByCategory(Category category);
    List<ItemCategory> findByItem(Item item);
}
