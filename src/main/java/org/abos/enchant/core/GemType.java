package org.abos.enchant.core;

public enum GemType implements Named {

    DIAMOND(20),
    EMERALD(10),
    RUBY(5),
    SAPPHIRE(7);

    /**
     * Must be non-negative!
     */
    private final transient int baseValue;

    GemType(int baseValue) {
        if (baseValue < 0)
            throw new IllegalArgumentException("Invalid gem base value!");
        this.baseValue = baseValue;
    }

    public int getBaseValue() {
        return baseValue;
    }

    @Override
    public String getName() {
        return name();
    }

    public static GemType random() {
        var values = GemType.values();
        return values[Util.RNG.nextInt(values.length)];
    }
}
