package org.abos.enchant.core;

import java.io.Serializable;

public interface Item extends Named, Serializable {

    ItemType getItemType();

    /**
     * Must be non-negative!
     */
    int getValue();

}
