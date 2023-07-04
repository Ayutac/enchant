package org.abos.enchant.core;

public class TestItemPricing {

    public static void main(String[] args) {
        UnenchantedItem sword = new UnenchantedItem(ItemType.SWORD);
        System.out.printf("%s: %dG%n", sword.getName(), sword.getValue());
        EnchantedItem enchantedSword = new EnchantedItem(sword, Spell.HEAT, 25);
        System.out.printf("%s: %dG%n", enchantedSword.getName(), enchantedSword.getValue());
        enchantedSword = new EnchantedItem(sword, Spell.HEAT, 50);
        System.out.printf("%s: %dG%n", enchantedSword.getName(), enchantedSword.getValue());
        enchantedSword = new EnchantedItem(sword, Spell.HEAT, 100);
        System.out.printf("%s: %dG%n", enchantedSword.getName(), enchantedSword.getValue());
        enchantedSword = new EnchantedItem(sword, Spell.FIRE, 50);
        System.out.printf("%s: %dG%n", enchantedSword.getName(), enchantedSword.getValue());
        enchantedSword = new EnchantedItem(sword, Spell.FIRE, 100);
        System.out.printf("%s: %dG%n", enchantedSword.getName(), enchantedSword.getValue());
        UnenchantedItem wand = new UnenchantedItem(ItemType.WAND);
        System.out.printf("%s: %dG%n", wand.getName(), wand.getValue());
        EnchantedItem enchantedWand = new EnchantedItem(wand, Spell.HEAT, 25);
        System.out.printf("%s: %dG%n", enchantedWand.getName(), enchantedWand.getValue());
        enchantedWand = new EnchantedItem(wand, Spell.HEAT, 50);
        System.out.printf("%s: %dG%n", enchantedWand.getName(), enchantedWand.getValue());
        enchantedWand = new EnchantedItem(wand, Spell.HEAT, 100);
        System.out.printf("%s: %dG%n", enchantedWand.getName(), enchantedWand.getValue());
        enchantedWand = new EnchantedItem(wand, Spell.FIRE_BOLT, 50);
        System.out.printf("%s: %dG%n", enchantedWand.getName(), enchantedWand.getValue());
        enchantedWand = new EnchantedItem(wand, Spell.FIRE_BOLT, 100);
        System.out.printf("%s: %dG%n", enchantedWand.getName(), enchantedWand.getValue());
    }

}
