package com.farmus.backend.domain.item.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ItemOption {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private String optionName;
    private String optionValue;
    private int optionPrice;

    protected ItemOption(Long id, Item item, String optionName, String optionValue, int optionPrice) {
        this.id = id;
        this.item = item;
        this.optionName = optionName;
        this.optionValue = optionValue;
        this.optionPrice = optionPrice;
    }

    public static ItemOption of(Item item, String optionName, String optionValue, int optionPrice) {
        ItemOption option = new ItemOption();
        option.item = item;
        option.optionName = optionName;
        option.optionValue = optionValue;
        option.optionPrice = optionPrice;
        return option;
    }
}
