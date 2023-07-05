package org.abos.enchant.core;

import java.util.Objects;
import java.util.function.Predicate;

public enum Action implements Named {
    
    CHANGE_LOCATION,
    COLLECT_MATERIAL,
    DISENCHANT(player -> player.getInventory().stream().anyMatch(item -> item instanceof EnchantedItem)),
    ENCHANT(player -> player.getInventory().stream().anyMatch(item -> item instanceof UnenchantedItem)),
    EXIT,
    EXPERIMENT(player -> player.getSpellTome().size() != Spell.values().length),
    INVENTORY,
    LOAD,
    MEDITATE(player -> player.getSpellTome().size() != Spell.values().length),
    NEW_GAME,
    SAVE,
    SHOP,
    SLEEP,
    STATS,
    TEACH_KIDS;

    public static final int CHANGE_LOCATION_STAMINA = 2;
    public static final int DISENCHANTING_STAMINA = 10;
    public static final int ENCHANTING_STAMINA = 20;
    public static final int EXPERIMENTING_STAMINA = 20;
    public static final int MEDITATION_STAMINA = 10;

    private final transient Predicate<Player> prerequisite;
    
    Action() {
        this(player -> true);
    }

    Action(final Predicate<Player> prerequisite) {
        this.prerequisite = Objects.requireNonNull(prerequisite);
    }

    public boolean prerequisiteFulfilledBy(final Player player) {
        return prerequisite.test(player);
    }

    @Override
    public String getName() {
        return name();
    }
}
