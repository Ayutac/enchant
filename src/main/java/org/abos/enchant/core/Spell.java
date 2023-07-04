package org.abos.enchant.core;

import java.util.function.BiFunction;
import java.util.function.Function;

public enum Spell implements Named {

    AIR(4),
    AIR_BOLT(4),
    BREEZE(1),
    COLD(1),
    CONDENSE(3),
    DARK(3),
    DARKNESS(3),
    DARKNESS_BOLT(3),
    EARTH(4),
    EARTH_BOLT(4),
    ENERGY(4),
    FIRE(4),
    FIRE_BOLT(4),
    FLASHBANG(3),
    FORCE_BOLT(3),
    FREEZE(1),
    HEAT(1),
    ICE(4),
    ICE_BOLT(4),
    IGNITE(3),
    LIGHT(3),
    LIGHT_BOLT(3),
    MUFFLE(3),
    NOISE(3),
    NOTE(3),
    PRESSURE(2),
    SHINE(3),
    SILENCE(4),
    SLOW(3),
    SOLIDIFY(3),
    SOUND(3),
    SPEED(3),
    STATIC(2),
    VELOCITY(3),
    VIBRATION(3),
    WARMTH(1),
    WATER(4),
    WATER_BOLT(4),
    WIND(2);

    /**
     * Must be positive!
     */
    final int complexity;

    Spell(int complexity) {
        if (complexity < 1 || complexity > 20) // MAX COMPLEXITY
            throw new IllegalArgumentException("Invalid spell complexity!");
        this.complexity = complexity;
    }

    public int getComplexity() {
        return complexity;
    }

    @Override
    public String getName() {
        return name();
    }

    public static final Function<Spell, Spell> OPP = new Function<>() {
        @Override
        public Spell apply(Spell spell) {
            return switch (spell) {
                case WARMTH -> COLD;
                case COLD -> WARMTH;
                case FREEZE -> HEAT;
                case HEAT -> FREEZE;
                case LIGHT -> DARK;
                case DARK -> LIGHT;
                case NOISE -> SILENCE;
                case SILENCE -> NOISE;
                case SOUND -> MUFFLE;
                case MUFFLE -> SOUND;
                case SPEED -> SLOW;
                case SLOW -> SPEED;
                case VELOCITY -> VIBRATION;
                case VIBRATION -> VELOCITY;
                default -> null;
            };
        }
    };

    public static final BiFunction<Spell, Spell, Spell> COMB = new BiFunction<>() {
        @Override
        public Spell apply(Spell spell1, Spell spell2) {
            if (comb(spell1, spell2, NOTE))
                return NOISE;
            if (comb(spell1, spell2, NOTE, NOISE))
                return SOUND;
            if (comb(spell1, spell2, MUFFLE))
                return SILENCE;
            if (comb(spell1, spell2, NOISE, SHINE))
                return FLASHBANG;
            if (comb(spell1, spell2, PRESSURE))
                return VELOCITY;
            if (comb(spell1, spell2, PRESSURE, VELOCITY))
                return FORCE_BOLT;
            if (comb(spell1, spell2, WARMTH, COLD))
                return WIND;
            if (comb(spell1, spell2, WIND, VELOCITY))
                return AIR_BOLT;
            if (comb(spell1, spell2, WIND))
                return PRESSURE;
            if (comb(spell1, spell2, LIGHT, VELOCITY))
                return LIGHT_BOLT;
            if (comb(spell1, spell2, DARKNESS, VELOCITY))
                return DARKNESS_BOLT;
            if (comb(spell1, spell2, LIGHT))
                return SHINE;
            if (comb(spell1, spell2, DARK))
                return DARKNESS;
            if (comb(spell1, spell2, VELOCITY))
                return SPEED;
            if (comb(spell1, spell2, WIND, COLD))
                return BREEZE;
            if (comb(spell1, spell2, WARMTH))
                return HEAT;
            if (comb(spell1, spell2, COLD))
                return FREEZE;
            if (comb(spell1, spell2, HEAT))
                return IGNITE;
            if (comb(spell1, spell2, COLD, PRESSURE))
                return CONDENSE;
            if (comb(spell1, spell2, CONDENSE, PRESSURE))
                return SOLIDIFY;
            if (comb(spell1, spell2, SLOW, LIGHT))
                return ENERGY;
            if (comb(spell1, spell2, ENERGY, IGNITE))
                return FIRE;
            if (comb(spell1, spell2, ENERGY, CONDENSE))
                return WATER;
            if (comb(spell1, spell2, ENERGY, WIND))
                return AIR;
            if (comb(spell1, spell2, ENERGY, FREEZE))
                return ICE;
            if (comb(spell1, spell2, WATER, FREEZE))
                return ICE;
            if (comb(spell1, spell2, ENERGY, SOLIDIFY))
                return EARTH;
            if (comb(spell1, spell2, VIBRATION))
                return NOTE;
            if (comb(spell1, spell2, HEAT, FREEZE))
                return STATIC;
            if (comb(spell1, spell2, IGNITE, FREEZE))
                return LIGHT;
            if (comb(spell1, spell2, FIRE, VELOCITY))
                return FIRE_BOLT;
            if (comb(spell1, spell2, WATER, VELOCITY))
                return WATER_BOLT;
            if (comb(spell1, spell2, ICE, VELOCITY))
                return ICE_BOLT;
            if (comb(spell1, spell2, WATER_BOLT, FREEZE))
                return ICE_BOLT;
            if (comb(spell1, spell2, EARTH, VELOCITY))
                return EARTH_BOLT;
            return null;
        }
    };

    static boolean comb(Spell act1, Spell act2, Spell exp) {
        return act1 == exp && act2 == exp;
    }

    static boolean comb(Spell act1, Spell act2, Spell exp1, Spell exp2) {
        return ((act1 == exp1 && act2 == exp2) || (act1 == exp2 && act2 == exp1));
    }

}
