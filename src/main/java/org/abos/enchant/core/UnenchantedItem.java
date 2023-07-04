package org.abos.enchant.core;

import java.util.Objects;

public class UnenchantedItem implements Item {

    private final ItemType type;

    public UnenchantedItem(ItemType type) {
        this.type = Objects.requireNonNull(type);
    }

    @Override
    public ItemType getItemType() {
        return type;
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public int getValue() {
        return type.getBaseValue();
    }
}
