package org.abos.enchant.core;

import java.util.Arrays;
import java.util.Objects;

public enum Location implements Named {

    ACADEMY(Action.TEACH_KIDS, Action.INVENTORY, Action.STATS, Action.CHANGE_LOCATION),
    FOREST(Action.COLLECT_MATERIAL, Action.INVENTORY, Action.STATS, Action.CHANGE_LOCATION),
    MERCHANT(Action.SHOP, Action.STATS, Action.CHANGE_LOCATION),
    PLAYER_ROOM(Action.MEDITATE, Action.EXPERIMENT, Action.ENCHANT, Action.DISENCHANT, Action.SLEEP, Action.INVENTORY, Action.STATS, Action.CHANGE_LOCATION, Action.SAVE, Action.LOAD, Action.EXIT);

    private final transient Action[] actions;

    Location(Action... actions) {
        this.actions = Objects.requireNonNull(actions);
    }

    public Action[] getActionsFor(final Player player) {
        return Arrays.stream(actions).filter(action -> action.prerequisiteFulfilledBy(player)).toArray(Action[]::new);
    }

    @Override
    public String getName() {
        return name();
    }
}
