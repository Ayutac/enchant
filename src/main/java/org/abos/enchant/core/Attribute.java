package org.abos.enchant.core;

public enum Attribute implements Named {

    DEXTERITY(false),
    INTELLIGENCE(true),
    VITALITY(false);

    private final boolean hidden;

    Attribute(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }

    @Override
    public String getName() {
        return name();
    }
}
