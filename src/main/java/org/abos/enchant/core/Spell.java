package org.abos.enchant.core;

import java.util.function.BiFunction;
import java.util.function.Function;

public enum Spell implements Named {

    AIR(4),
    AIR_BARRIER(6),
    AIR_BOLT(4),
    AIR_DOME(7),
    BARRIER(5),
    BREEZE(1),
    CLEANSE(5),
    COLD(1),
    CONDENSE(3),
    DARK(3),
    DARKNESS(3),
    DARKNESS_BARRIER(6),
    DARKNESS_BOLT(3),
    DARKNESS_DOME(7),
    DOME(6),
    EARTH(4),
    EARTH_BARRIER(6),
    EARTH_BOLT(4),
    EARTH_DOME(7),
    ELECTRICITY(3),
    ENERGY(4),
    EXTINGUISH(3),
    FIRE(4),
    FIRE_BARRIER(6),
    FIRE_BOLT(4),
    FIRE_DOME(7),
    FLASHBANG(3),
    FORCE_BARRIER(6),
    FORCE_BOLT(3),
    FORCE_DOME(7),
    FREEZE(1),
    GLASS(6),
    GLASS_BARRIER(7),
    GLASS_BOLT(6),
    GLASS_DOME(8),
    HEAT(1),
    ICE(4),
    ICE_BARRIER(6),
    ICE_BOLT(4),
    ICE_DOME(7),
    IGNITE(3),
    LIGHT(3),
    LIGHT_BARRIER(6),
    LIGHT_BOLT(3),
    LIGHT_DOME(7),
    MAGMA(6),
    MAGMA_BARRIER(7),
    MAGMA_BOLT(6),
    MAGMA_DOME(8),
    MUD(5),
    MUD_BARRIER(6),
    MUD_BOLT(5),
    MUD_DOME(7),
    MUFFLE(3),
    NOISE(3),
    NOTE(3),
    PRESSURE(2),
    SAND(5),
    SAND_BARRIER(6),
    SAND_DOME(7),
    SHINE(3),
    SILENCE(4),
    SLOW(3),
    SOLIDIFY(3),
    SOUND(3),
    SPEED(3),
    STATIC(2),
    STONE(5),
    STONE_BARRIER(6),
    STONE_BOLT(5),
    STONE_DOME(7),
    VELOCITY(3),
    VIBRATION(3),
    WARMTH(1),
    WATER(4),
    WATER_BARRIER(6),
    WATER_BOLT(4),
    WATER_DOME(7),
    WIND(2);

    /**
     * Must be positive!
     */
    private final transient int complexity;

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
                case IGNITE -> EXTINGUISH;
                case EXTINGUISH -> IGNITE;
                case MUD -> CLEANSE;
                case CLEANSE -> MUD;
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
            if (comb(spell1, spell2, WATER, SOLIDIFY))
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
            if (comb(spell1, spell2, WATER_BOLT, SOLIDIFY))
                return ICE_BOLT;
            if (comb(spell1, spell2, EARTH, VELOCITY))
                return EARTH_BOLT;
            if (comb(spell1, spell2, FREEZE))
                return EXTINGUISH;
            if (comb(spell1, spell2, WATER, EARTH))
                return MUD;
            if (comb(spell1, spell2, MUD, VELOCITY))
                return MUD_BOLT;
            if (comb(spell1, spell2, WATER_BOLT, EARTH))
                return MUD_BOLT;
            if (comb(spell1, spell2, EARTH_BOLT, WATER))
                return MUD_BOLT;
            if (comb(spell1, spell2, EARTH, VIBRATION))
                return SAND;
            if (comb(spell1, spell2, STATIC))
                return ELECTRICITY;
            if (comb(spell1, spell2, SOLIDIFY, EARTH))
                return STONE;
            if (comb(spell1, spell2, VELOCITY, STONE))
                return STONE_BOLT;
            if (comb(spell1, spell2, SOLIDIFY, EARTH_BOLT))
                return STONE_BOLT;
            if (comb(spell1, spell2, HEAT, STONE))
                return MAGMA;
            if (comb(spell1, spell2, VELOCITY, MAGMA))
                return MAGMA_BOLT;
            if (comb(spell1, spell2, HEAT, STONE_BOLT))
                return MAGMA_BOLT;
            if (comb(spell1, spell2, HEAT, SAND))
                return GLASS;
            if (comb(spell1, spell2, VELOCITY, GLASS))
                return GLASS_BOLT;
            if (comb(spell1, spell2, SOLIDIFY))
                return BARRIER;
            if (comb(spell1, spell2, BARRIER, AIR))
                return AIR_BARRIER;
            if (comb(spell1, spell2, BARRIER, WATER))
                return WATER_BARRIER;
            if (comb(spell1, spell2, BARRIER, ICE))
                return ICE_BARRIER;
            if (comb(spell1, spell2, WATER_BARRIER, FREEZE))
                return ICE_BARRIER;
            if (comb(spell1, spell2, WATER_BARRIER, SOLIDIFY))
                return ICE_BARRIER;
            if (comb(spell1, spell2, BARRIER, FIRE))
                return FIRE_BARRIER;
            if (comb(spell1, spell2, BARRIER, EARTH))
                return EARTH_BARRIER;
            if (comb(spell1, spell2, BARRIER, MUD))
                return MUD_BARRIER;
            if (comb(spell1, spell2, WATER_BARRIER, EARTH))
                return MUD_BARRIER;
            if (comb(spell1, spell2, EARTH_BARRIER, WATER))
                return MUD_BARRIER;
            if (comb(spell1, spell2, BARRIER, SAND))
                return SAND_BARRIER;
            if (comb(spell1, spell2, BARRIER, GLASS))
                return GLASS_BARRIER;
            if (comb(spell1, spell2, SAND_BARRIER, HEAT))
                return GLASS_BARRIER;
            if (comb(spell1, spell2, BARRIER, MAGMA))
                return MAGMA_BARRIER;
            if (comb(spell1, spell2, EARTH_BARRIER, HEAT))
                return MAGMA_BARRIER;
            if (comb(spell1, spell2, BARRIER, LIGHT))
                return LIGHT_BARRIER;
            if (comb(spell1, spell2, BARRIER, DARKNESS))
                return DARKNESS_BARRIER;
            if (comb(spell1, spell2, BARRIER, PRESSURE))
                return FORCE_BARRIER;
            if (comb(spell1, spell2, BARRIER, STONE))
                return STONE_BARRIER;
            if (comb(spell1, spell2, BARRIER))
                return DOME;
            if (comb(spell1, spell2, DOME, AIR))
                return AIR_DOME;
            if (comb(spell1, spell2, DOME, WATER))
                return WATER_DOME;
            if (comb(spell1, spell2, DOME, ICE))
                return ICE_DOME;
            if (comb(spell1, spell2, WATER_DOME, FREEZE))
                return ICE_DOME;
            if (comb(spell1, spell2, WATER_DOME, SOLIDIFY))
                return ICE_DOME;
            if (comb(spell1, spell2, DOME, FIRE))
                return FIRE_DOME;
            if (comb(spell1, spell2, DOME, EARTH))
                return EARTH_DOME;
            if (comb(spell1, spell2, DOME, MUD))
                return MUD_DOME;
            if (comb(spell1, spell2, WATER_DOME, EARTH))
                return MUD_DOME;
            if (comb(spell1, spell2, EARTH_DOME, WATER))
                return MUD_DOME;
            if (comb(spell1, spell2, DOME, SAND))
                return SAND_DOME;
            if (comb(spell1, spell2, DOME, GLASS))
                return GLASS_DOME;
            if (comb(spell1, spell2, SAND_DOME, HEAT))
                return GLASS_DOME;
            if (comb(spell1, spell2, DOME, MAGMA))
                return MAGMA_DOME;
            if (comb(spell1, spell2, EARTH_DOME, HEAT))
                return MAGMA_DOME;
            if (comb(spell1, spell2, DOME, LIGHT))
                return LIGHT_DOME;
            if (comb(spell1, spell2, DOME, DARKNESS))
                return DARKNESS_DOME;
            if (comb(spell1, spell2, DOME, PRESSURE))
                return FORCE_DOME;
            if (comb(spell1, spell2, DOME, STONE))
                return STONE_DOME;
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
