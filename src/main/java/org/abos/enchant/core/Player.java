package org.abos.enchant.core;

import java.util.*;

public class Player {

    public static final int EXP_PER_LEVEL = 200;

    private final Map<Attribute, Integer> attributes = new EnumMap<>(Attribute.class);

    private final Map<Skill, Integer> skills = new EnumMap<>(Skill.class);

    private final Map<Skill, Integer> skillsExp = new EnumMap<>(Skill.class);

    private final Map<Bar, Integer> currentBars = new EnumMap<>(Bar.class);

    private final EnumSet<Spell> spellTome = EnumSet.noneOf(Spell.class);

    private final List<Item> inventory = new LinkedList<>();

    private int gold = 0;

    private Location location = Location.PLAYER_ROOM;

    private int day = 0;

    public Player() {
        for (Attribute attribute : Attribute.values()) {
            attributes.put(attribute, 8);
        }
        for (Skill skill : Skill.values()) {
            skills.put(skill, 4);
            skillsExp.put(skill, 0);
        }
        for (Bar bar : Bar.values()) {
            currentBars.put(bar, getMaxBar(bar));
        }
        spellTome.add(Spell.WARMTH);
    }

    public int getAttribute(final Attribute attribute) {
        return attributes.get(Objects.requireNonNull(attribute));
    }

    /**
     * @param value Must be positive!
     */
    public void setAttribute(final Attribute attribute, final int value) {
        if (value <= 0)
            throw new IllegalArgumentException("Attribute value must be positive!");
        attributes.put(attribute, value);
    }

    public int getSkill(final Skill skill) {
        return skills.get(Objects.requireNonNull(skill));
    }

    /**
     * @param value Must be non-negative!
     */
    public void setSkill(final Skill skill, final int value) {
        if (value < 0)
            throw new IllegalArgumentException("Skill value must be non-negative!");
        skills.put(skill, value);
    }

    public int getSkillExp(final Skill skill) {
        return skillsExp.get(Objects.requireNonNull(skill));
    }

    /**
     * @param amount Must be non-negative!
     * @return How many times levelled up.
     */
    public int increaseSkillExp(final Skill skill, final int amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount must be non-negative!");
        int newExp = getSkillExp(skill) + amount;
        if (newExp >= EXP_PER_LEVEL) {
            skillsExp.put(skill, newExp % EXP_PER_LEVEL);
            int extraLevels = newExp / EXP_PER_LEVEL;
            int oldSkillLevel = getSkill(skill);
            // overflow protection
            skills.put(skill, Math.max(oldSkillLevel, oldSkillLevel + extraLevels));
            return extraLevels;
        }
        skillsExp.put(skill, newExp);
        return 0;
    }

    public int getEffectiveSkill(final Skill skill) {
        return getAttribute(skill.getBase())+(getSkill(skill)-4)/4;
    }

    public int getCurrentBar(final Bar bar) {
        return currentBars.get(Objects.requireNonNull(bar));
    }

    public void decreaseBar(final Bar bar, final int amount) {
        int current = getCurrentBar(Objects.requireNonNull(bar));
        if (amount > current) {
            throw new IllegalArgumentException("Amount to decrease is too great!");
        }
        currentBars.put(bar, current - amount);
    }

    public int getMaxBar(final Bar bar) {
        int value = 100 + 10*attributes.get(bar.getBase());
        return value;
    }

    public EnumSet<Spell> getSpellTome() {
        return spellTome;
    }

    public boolean knows(Spell spell) {
        return spellTome.contains(spell);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public int getGold() {
        return gold;
    }

    /**
     * @param gold Must be non-negative!
     */
    public void setGold(int gold) {
        if (gold < 0)
            throw new IllegalArgumentException("Negative gold amount not possible!");
        this.gold = gold;
    }

    public void increaseGold(int amount) {
        setGold(getGold()+amount);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = Objects.requireNonNull(location);
    }

    public boolean checkGoTo(final Location location) {
        return location == Location.PLAYER_ROOM || getCurrentBar(Bar.STAMINA) >= Action.CHANGE_LOCATION_STAMINA;
    }

    public void goTo(final Location location) {
        if (location != Location.PLAYER_ROOM) {
            decreaseBar(Bar.STAMINA, Action.CHANGE_LOCATION_STAMINA);
        }
        setLocation(location);
    }

    public int getDay() {
        return day;
    }

    public void incrementDay() {
        day++;
    }

    public void sleep() {
        for (Bar bar : Bar.values()) {
            currentBars.put(bar, getMaxBar(bar));
        }
        incrementDay();
    }

    public boolean checkMeditate(final Spell spell) {
        return getCurrentBar(Bar.STAMINA) >= Action.MEDITATION_STAMINA
                && getCurrentBar(Bar.MANA) >= 2*spell.getComplexity();
    }

    public Spell meditate(final Spell spell) {
        if (!knows(spell)) {
            throw new IllegalArgumentException("Player doesn't know the spell!");
        }
        // roll for disenchanting
        decreaseBar(Bar.STAMINA, Action.MEDITATION_STAMINA);
        decreaseBar(Bar.MANA, 2*spell.getComplexity());
        final int imaginationResult = Util.rollAgainst(getEffectiveSkill(Skill.IMAGINATION) - spell.getComplexity()/2);
        final int logicResult = Util.rollAgainst(getEffectiveSkill(Skill.LOGIC) - spell.getComplexity()/2);
        final Spell result = Spell.OPP.apply(spell);
        // give XP
        if (imaginationResult >= 0) {
            if (logicResult >= 0) {
                increaseSkillExp(Skill.INSCRIPTION, 30 * spell.getComplexity());
                increaseSkillExp(Skill.WILLPOWER, 15 * spell.getComplexity());
            }
            else {
                increaseSkillExp(Skill.INSCRIPTION, 10 * spell.getComplexity());
            }
        }
        else if (logicResult >= 0) {
            increaseSkillExp(Skill.WILLPOWER, 4 * spell.getComplexity());
        }
        // calculate result
        if (imaginationResult < 0 || logicResult < 0 || result == null) {
            return null;
        }
        spellTome.add(result);
        return result;
    }

    public boolean checkExperiment(final Spell spell1, final Spell spell2) {
        return getCurrentBar(Bar.STAMINA) >= Action.EXPERIMENTING_STAMINA
                && getCurrentBar(Bar.MANA) >= 2*(spell1.getComplexity() + spell2.getComplexity());
    }

    public Spell experiment(final Spell spell1, final Spell spell2) {
        if (!knows(spell1) || !knows(spell2)) {
            throw new IllegalArgumentException("Player doesn't know the spell!");
        }
        // roll for disenchanting
        decreaseBar(Bar.STAMINA, Action.EXPERIMENTING_STAMINA);
        decreaseBar(Bar.MANA, 2*(spell1.getComplexity() + spell2.getComplexity()));
        final int imaginationResult = Util.rollAgainst(getEffectiveSkill(Skill.IMAGINATION) - (spell1.getComplexity() + spell2.getComplexity())/4);
        final int logicResult = Util.rollAgainst(getEffectiveSkill(Skill.LOGIC) - (spell1.getComplexity() + spell2.getComplexity())/4);
        final Spell result = Spell.COMB.apply(spell1, spell2);
        // give XP
        if (imaginationResult >= 0) {
            if (logicResult >= 0) {
                increaseSkillExp(Skill.INSCRIPTION, 15 * (spell1.getComplexity() + spell2.getComplexity())/2);
                increaseSkillExp(Skill.WILLPOWER, 30 * (spell1.getComplexity() + spell2.getComplexity())/2);
            }
            else {
                increaseSkillExp(Skill.INSCRIPTION, 4 * (spell1.getComplexity() + spell2.getComplexity())/2);
            }
        }
        else if (logicResult >= 0) {
            increaseSkillExp(Skill.WILLPOWER, 10 * (spell1.getComplexity() + spell2.getComplexity())/2);
        }
        // calculate result
        if (imaginationResult < 0 || logicResult < 0 || result == null) {
            return null;
        }
        spellTome.add(result);
        return result;
    }

    public boolean checkEnchant(final Spell enchantment) {
        return getCurrentBar(Bar.STAMINA) >= Action.ENCHANTING_STAMINA
                && getCurrentBar(Bar.MANA) >= enchantment.getComplexity();
    }

    /**
     * @return The enchanted item or <code>null</code> if the item was destroyed in the process.
     * @throws IllegalArgumentException If the given parameters don't fit the player.
     */
    public EnchantedItem enchant(final UnenchantedItem item, final Spell enchantment, final List<Gem> gems) {
        if (!knows(enchantment)) {
            throw new IllegalArgumentException("Player doesn't know the spell!");
        }
        final List<Gem> neededGems = new LinkedList<>();
        int lastGemSize = Util.calculateGemUsage(enchantment, neededGems, gems);
        if (neededGems.isEmpty()) {
            throw new AssertionError("Needed gems cannot be empty!");
        }
        ListIterator<Gem> it = neededGems.listIterator();
        // remove the gems from inventory if possible
        while (it.hasNext()) {
            if (!getInventory().remove(it.next())) {
                it.previous();
                while (it.hasPrevious()) {
                    getInventory().add(it.previous());
                }
                throw new IllegalArgumentException("Player not in possession of needed gems!");
            }
        }
        // remove the item if possible
        if (!getInventory().remove(item)) {
            getInventory().addAll(neededGems);
            throw new IllegalArgumentException("Player not in possession of item!");
        }
        // roll for enchanting
        decreaseBar(Bar.STAMINA, Action.ENCHANTING_STAMINA);
        decreaseBar(Bar.MANA, enchantment.complexity);
        final int gemsQuality = neededGems.stream().mapToInt(Gem::getQuality).min().getAsInt();
        final GemType firstGemType = neededGems.get(0).getGemType();
        final boolean mixed = neededGems.stream().anyMatch(gem -> gem.getGemType() != firstGemType);
        final int inscriptionResult = Util.rollAgainst(getEffectiveSkill(Skill.INSCRIPTION) - (Gem.MAX_QUALITY-gemsQuality)/3 - enchantment.getComplexity()/2);
        final int bindingResult = Util.rollAgainst(getEffectiveSkill(Skill.WILLPOWER) - (Gem.MAX_QUALITY-gemsQuality)/3 - enchantment.getComplexity()/2);
        // add remains of last gem
        final Gem lastGem = neededGems.get(neededGems.size()-1);
        if (lastGemSize > 0) {
            lastGem.setSize(lastGemSize);
            getInventory().add(lastGem);
        }
        // give XP
        if (inscriptionResult >= 0) {
            increaseSkillExp(Skill.INSCRIPTION, 25*enchantment.getComplexity());
        }
        else if (inscriptionResult >= -3) {
            increaseSkillExp(Skill.INSCRIPTION, 10);
        }
        if (bindingResult >= 0) {
            increaseSkillExp(Skill.WILLPOWER, 10*enchantment.getComplexity());
        }
        else if (bindingResult >= -3) {
            increaseSkillExp(Skill.WILLPOWER, 5);
        }
        // calculate result
        if (inscriptionResult < -3 || bindingResult < -3) {
            return null;
        }
        int quality = (gemsQuality-1)*17 + 2*(inscriptionResult + bindingResult) + (mixed ? 0 : firstGemType.getBaseValue());
        quality = Math.min(Math.max(0, quality), 100);
        EnchantedItem enchantedItem = new EnchantedItem(item, enchantment, quality);
        getInventory().add(enchantedItem);
        return enchantedItem;
    }

    public boolean checkDisenchant() {
        return getCurrentBar(Bar.STAMINA) < Action.DISENCHANTING_STAMINA;
    }

    /**
     * @return The disenchanted item or <code>null</code> if the item was destroyed in the process.
     * @throws IllegalArgumentException If the given parameters don't fit the player.
     */
    public UnenchantedItem disenchant(final EnchantedItem item) {
        // remove the item if possible
        if (!getInventory().remove(item)) {
            throw new IllegalArgumentException("Player not in possession of item!");
        }
        // roll for disenchanting
        decreaseBar(Bar.STAMINA, Action.DISENCHANTING_STAMINA);
        final int inscriptionResult = Util.rollAgainst(getEffectiveSkill(Skill.INSCRIPTION) - item.getEnchantment().getComplexity()/2);
        final int bindingResult = Util.rollAgainst(getEffectiveSkill(Skill.WILLPOWER) - item.getEnchantment().getComplexity()/2);
        // give XP
        if (inscriptionResult >= 0) {
            increaseSkillExp(Skill.INSCRIPTION, 10*item.getEnchantment().getComplexity());
        }
        if (bindingResult >= 0) {
            increaseSkillExp(Skill.WILLPOWER, item.getEnchantment().getComplexity());
        }
        // calculate result
        if (inscriptionResult < 0 || bindingResult < 0) {
            return null;
        }
        inventory.add(item.getBase());
        return item.getBase();
    }
}
