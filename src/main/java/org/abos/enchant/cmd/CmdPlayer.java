package org.abos.enchant.cmd;

import org.abos.enchant.core.*;

import java.util.List;

public class CmdPlayer extends Player {

    public static final long serialVersionUID = 723493452L;

    @Override
    public int increaseSkillExp(final Skill skill, final int amount) {
        int levels = super.increaseSkillExp(skill, amount);
        if (levels > 0) {
            System.out.printf("You levelled up %s!%n", skill.getName());
            CmdNavigation.pause(500);
        }
        return levels;
    }

    @Override
    public boolean checkGoTo(final Location location) {
        boolean canGo = super.checkGoTo(location);
        if (!canGo) {
            System.out.println("You don't have enough stamina, go home to sleep!");
        }
        return canGo;
    }

    @Override
    public boolean checkMeditate(final Spell spell) {
        boolean canMeditate = super.checkMeditate(spell);
        if (!canMeditate) {
            System.out.println("You don't have enough energy for meditating!");
        }
        return canMeditate;
    }

    @Override
    public Spell meditate(final Spell spell) {
        Spell result = super.meditate(spell);
        if (result == null) {
            System.out.println("Your meditation was relaxing, but no new spell came out of it...");
        }
        else {
            System.out.printf("Your meditation was successful! You understood that %s is the opposite of %s!%n", spell.getName(), result.getName());
        }
        CmdNavigation.pause(500);
        return result;
    }

    @Override
    public boolean checkExperiment(final Spell spell1, final Spell spell2) {
        boolean canExperiment = super.checkExperiment(spell1, spell2);
        if (!canExperiment) {
            System.out.println("You don't have enough energy for experimenting!");
        }
        return canExperiment;
    }

    @Override
    public Spell experiment(final Spell spell1, final Spell spell2) {
        Spell result = super.experiment(spell1, spell2);
        if (result == null) {
            System.out.println("Your experimentation was fun, but no new spell came out of it...");
        }
        else {
            System.out.printf("Your experimentation was successful! You combined %s and %s into %s!%n", spell1.getName(), spell2.getName(), result.getName());
        }
        CmdNavigation.pause(500);
        return result;
    }

    @Override
    public boolean checkEnchant(final Spell enchantment) {
        boolean canEnchant = super.checkEnchant(enchantment);
        if (!canEnchant) {
            System.out.println("You don't have enough energy for enchanting!");
        }
        return canEnchant;
    }

    @Override
    public EnchantedItem enchant(final UnenchantedItem item, final Spell enchantment, final List<Gem> gems) {
        EnchantedItem enchantedItem = super.enchant(item, enchantment, gems);
        if (enchantedItem == null) {
            System.out.println("You failed and destroyed the item!");
        }
        else {
            System.out.printf("You created: %s%n", enchantedItem.getName());
        }
        CmdNavigation.pause(500);
        return enchantedItem;
    }

    @Override
    public boolean checkDisenchant() {
        boolean canDisenchant = super.checkDisenchant();
        if (!canDisenchant) {
            System.out.println("You don't have enough stamina for disenchanting!");
        }
        return canDisenchant;
    }

    @Override
    public UnenchantedItem disenchant(final EnchantedItem item) {
        UnenchantedItem unenchantedItem = super.disenchant(item);
        if (unenchantedItem == null) {
            System.out.println("You failed and destroyed the item!");
        }
        else {
            System.out.printf("You recovered: %s%n", unenchantedItem.getName());
        }
        CmdNavigation.pause(500);
        return unenchantedItem;
    }
}
