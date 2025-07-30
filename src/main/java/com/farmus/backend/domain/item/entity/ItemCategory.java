package com.farmus.backend.domain.item.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(
        name = "item_category",
        uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "category_id"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public ItemCategory(Long id, Item item, Category category) {
        this.id = id;
        this.item = item;
        this.category = category;
    }

    public static ItemCategory of(Item item, Category category) {
        return new ItemCategory(null, item, category);
    }
}
