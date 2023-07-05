package org.abos.enchant.core;

import java.util.Objects;

public enum Bar implements Named {

    STAMINA(Attribute.VITALITY),
    MANA(Attribute.INTELLIGENCE);

    private final transient Attribute base;

    Bar(Attribute base) {
        this.base = Objects.requireNonNull(base);
    }

    public Attribute getBase() {
        return base;
    }

    @Override
    public String getName() {
        return name();
    }
}
