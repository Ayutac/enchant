package org.abos.enchant.core;

import java.util.List;
import java.util.Random;

public final class Util {

    public final static Random RNG = new Random();

    private Util() {
        // no instantiation
    }

    public static int rollAgainst(int difficulty) {
        return difficulty - (3 + RNG.nextInt(6)+ RNG.nextInt(6)+ RNG.nextInt(6));
    }

    /**
     * Goes through the provided gems and gets the needed ones.
     * @return the remains of the last gem, if any
     * @throws IllegalArgumentException If the spell's complexity exceeds the provided gems.
     */
    public static int calculateGemUsage(Spell spell, List<Gem> neededGems, List<Gem> providedGems) {
        int complexity = spell.getComplexity();
        Gem nextGem = null;
        while (complexity > 0) {
            if (providedGems.isEmpty()) {
                throw new IllegalArgumentException("Provided gems are too few!");
            }
            nextGem = providedGems.get(0);
            providedGems.remove(0);
            complexity =- nextGem.getSize();
            neededGems.add(nextGem);
        }
        return -complexity;
    }

}
