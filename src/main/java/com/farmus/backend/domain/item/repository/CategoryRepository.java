package com.farmus.backend.domain.item.repository;

import com.farmus.backend.domain.item.entity.Category;
import com.farmus.backend.domain.item.exception.ItemErrorCode;
import com.farmus.backend.domain.item.exception.ItemException;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    default Category findOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new ItemException(ItemErrorCode.CATEGORY_NOT_FOUND));
    }

    List<Category> findAll();
}
