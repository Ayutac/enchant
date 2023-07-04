package org.abos.enchant.core;

import java.util.ArrayList;
import java.util.List;

public class TestEnchanting {

    public static void main(String[] args) {
        final Player player = new Player();
        player.setAttribute(Attribute.INTELLIGENCE, 12);
        player.setSkill(Skill.WILLPOWER, 9);
        final UnenchantedItem sword = new UnenchantedItem(ItemType.SWORD);
        System.out.println("High-quality Gem, low-quality player:");
        trySomeEnchantments(player, sword, Spell.HEAT, 5, 10);
        System.out.println();
        System.out.println("Low-quality Gem, low-quality player:");
        trySomeEnchantments(player, sword, Spell.HEAT, 2, 10);
        System.out.println();
        // high-quality player
        player.setSkill(Skill.WILLPOWER, 16);
        player.setSkill(Skill.INSCRIPTION, 16);
        System.out.println("High-quality Gem, high-quality player:");
        trySomeEnchantments(player, sword, Spell.HEAT, 5, 10);
        System.out.println();
        System.out.println("Low-quality Gem, high-quality player:");
        trySomeEnchantments(player, sword, Spell.HEAT, 2, 10);
    }

    public static void trySomeEnchantments(final Player player, final UnenchantedItem unenchanted, final Spell enchantment, final int quality, final int amount) {
        final Gem gem = new Gem(GemType.EMERALD, enchantment.getComplexity(), quality);
        EnchantedItem item;
        for (int i = 0; i < amount; i++) {
            player.getInventory().add(unenchanted);
            player.getInventory().add(gem);
            item = player.enchant(unenchanted, enchantment, new ArrayList<>(List.of(gem)));
            if (item == null)
                System.out.println("Item destroyed!");
            else
                System.out.println(item.getName());
        }
    }

}
