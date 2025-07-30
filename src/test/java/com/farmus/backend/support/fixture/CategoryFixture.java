package com.farmus.backend.support.fixture;

import com.farmus.backend.domain.item.entity.Category;
import lombok.Getter;

@Getter
public enum CategoryFixture {

    과일("과일", null),
    복숭아("복숭아", 과일());

    private String categoryName;
    private Category parentCategory;

    CategoryFixture(String categoryName, Category parentCategory) {
        this.categoryName = categoryName;
        this.parentCategory = parentCategory;
    }

    public static Category 과일(){
        return new Category(과일.categoryName, null);
    }

    public static Category 복숭아(Category parent){
        return new Category(복숭아.categoryName, parent);
    }
}
