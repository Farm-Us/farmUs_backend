package com.farmus.backend.domain.item.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    public Category(Long id, String categoryName, Category parentCategory) {
        this.id = id;
        this.categoryName = categoryName;
        this.parentCategory = parentCategory;
    }

    public Category(String categoryName, Category parentCategory) {
        this(null, categoryName, parentCategory);
    }
}
