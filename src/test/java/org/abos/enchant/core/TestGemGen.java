package org.abos.enchant.core;

import java.util.function.Supplier;

public class TestGemGen {

    public static void main(String[] args) {
        System.out.println("Random low gems:");
        trySomeGems(Gem::randomLow, 10);
        System.out.println();
        System.out.println("Random mid gems:");
        trySomeGems(Gem::randomMid, 10);
        System.out.println();
        System.out.println("Random high gems:");
        trySomeGems(Gem::randomHigh, 10);
    }

    public static void trySomeGems(final Supplier<Gem> gemSupplier, final int amount) {
        Gem gem;
        for (int i = 0; i < amount; i++) {
            gem = gemSupplier.get();
            System.out.printf("%s, value: %dG%n", gem.getName(), gem.getValue());
        }
    }

}
