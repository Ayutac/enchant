package org.abos.enchant.core;

import java.util.Objects;

public class Gem extends UnenchantedItem {

    public static final long serialVersionUID = 9872345234523L;

    public static final int MAX_SIZE = 10;

    public static final int MAX_QUALITY = 5;

    private final GemType type;

    private int size;

    private final int quality;

    private final transient String name;

    public Gem(GemType type, int size, int quality) {
        super(ItemType.STONE);
        this.type = Objects.requireNonNull(type);
        if (size < 1 || size > MAX_SIZE)
            throw new IllegalArgumentException("Invalid gem size!");
        this.size = size;
        if (quality < 1 || quality > MAX_QUALITY)
            throw new IllegalArgumentException("Invalid gem quality!");
        this.quality = quality;
        this.name = type.name() + ", size: " + size + ", quality: " + quality;
    }

    public GemType getGemType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int value) {
        if (quality < 1 || quality > MAX_QUALITY)
            throw new IllegalArgumentException("Invalid gem quality!");
        this.size = value;
    }

    public int getQuality() {
        return quality;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getValue() {
        return Math.max(1, type.getBaseValue()*quality*size/3);
    }

    public static Gem randomLow() {
        return new Gem(GemType.random(), Math.min(MAX_SIZE, (int)Math.round(Util.RNG.nextExponential()*3)+1), Math.min(MAX_QUALITY, (int)Math.round(Util.RNG.nextExponential()*2)+1));
    }

    public static Gem randomMid() {
        return new Gem(GemType.random(), Util.RNG.nextInt(MAX_SIZE)+1, Util.RNG.nextInt(MAX_QUALITY)+1);
    }

    public static Gem randomHigh() {
        return new Gem(GemType.random(), Math.max(1, (int)Math.round(MAX_SIZE-Util.RNG.nextExponential()*3)), Math.max(1, (int)Math.round(MAX_QUALITY-Util.RNG.nextExponential())));
    }

}
