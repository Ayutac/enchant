package org.abos.enchant.core;

public enum ItemType implements Named {

    ARROW(5, 5d),
    AXE(20, 2d),
    BOW(20, 2d),
    CROSSBOW(20, 2d),
    DAGGER(10, 3d),
    MACE(20, 2d),
    SCROLL(2, 10d),
    STONE(1, 10d),
    SWORD(20, 2d),
    WAND(1, 50d);

    private final transient int baseValue;

    private final transient double enchantFactor;

    ItemType(int baseValue, double enchantFactor) {
        if (baseValue < 1)
            throw new IllegalArgumentException("Base Value must be positive!");
        this.baseValue = baseValue;
        if (enchantFactor < 1d)
            throw new IllegalArgumentException("Enchant factor must be greater than or equal to 1!");
        this.enchantFactor = enchantFactor;
    }

    public int getBaseValue() {
        return baseValue;
    }

    public double getEnchantFactor() {
        return enchantFactor;
    }

    @Override
    public String getName() {
        return name();
    }
}
