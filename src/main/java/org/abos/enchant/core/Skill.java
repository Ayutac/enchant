package org.abos.enchant.core;

import java.util.Objects;

public enum Skill implements Named {

    INSCRIPTION(Attribute.DEXTERITY, true),
    IMAGINATION(Attribute.INTELLIGENCE, false),
    LOGIC(Attribute.INTELLIGENCE, false),
    WILLPOWER(Attribute.INTELLIGENCE, false);

    final Attribute base;

    final boolean hidden;

    Skill(Attribute base, boolean hidden) {
        this.base = Objects.requireNonNull(base);
        this.hidden = hidden;
    }

    public Attribute getBase() {
        return base;
    }

    public boolean isHidden() {
        return hidden;
    }

    @Override
    public String getName() {
        return name();
    }
}
