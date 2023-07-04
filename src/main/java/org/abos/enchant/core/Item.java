package org.abos.enchant.core;

public interface Item extends Named {

    ItemType getItemType();

    /**
     * Must be non-negative!
     */
    int getValue();

}
