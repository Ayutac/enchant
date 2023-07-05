package org.abos.enchant.core;

public class EnchantedItem implements Item {

    public static final long serialVersionUID = 6502983485L;

    private final UnenchantedItem base;

    public final int MAX_QUALITY = 100;

    private final Spell enchantment;

    private final int quality;

    /**
     * @param quality Must be between 0 and 100 inclusive!
     */
    public EnchantedItem(UnenchantedItem base, Spell enchantment, int quality) {
        this.base = base;
        this.enchantment = enchantment;
        if (quality < 0 || quality > MAX_QUALITY)
            throw new IllegalArgumentException("Enchantment quality is invalid!");
        this.quality = quality;
    }

    @Override
    public String getName() {
        return base.getName() + ", enchantment: " + enchantment.name() + ", quality: " + quality;
    }

    @Override
    public ItemType getItemType() {
        return base.getItemType();
    }

    @Override
    public int getValue() {
        return (int)(base.getValue()*getItemType().getEnchantFactor()*enchantment.getComplexity()*enchantment.getComplexity()/4*Math.sqrt(quality/10d));
    }

    public UnenchantedItem getBase() {
        return base;
    }

    public Spell getEnchantment() {
        return enchantment;
    }

    public int getQuality() {
        return quality;
    }
}
