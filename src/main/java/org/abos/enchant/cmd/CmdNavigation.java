package org.abos.enchant.cmd;

import org.abos.enchant.core.*;

import java.util.*;
import java.util.stream.Stream;

public class CmdNavigation {

    protected CmdNavigation() {
        /* no instantiation (for now) */
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CmdPlayer player = createPlayer(scanner);
        Location location;
        Action[] actions;
        Action selectedAction = null;
        while (selectedAction != Action.EXIT) {
            location = player.getLocation();
            System.out.printf("You are in %s.%n", location.getName());
            actions = player.getLocation().getActionsFor(player);
            displayOptions(actions);
            selectedAction = actions[select(actions.length, scanner, "Choose an action: ")];
            System.out.println();
            // consequences
            switch (selectedAction) {
                case CHANGE_LOCATION -> changeLocation(player, scanner);
                case DISENCHANT -> disenchant(player, scanner);
                case ENCHANT -> enchant(player, scanner);
                case EXIT -> System.out.println("Thanks for playing!");
                case EXPERIMENT -> experiment(player, scanner);
                case INVENTORY -> displayInventory(player);
                case MEDITATE -> meditate(player, scanner);
                case SLEEP -> sleep(player);
                case STATS -> displayStats(player);
                default -> System.out.println("Currently not supported!");
            }
            System.out.println();
        }
    }

    public static CmdPlayer createPlayer(final Scanner scanner) {
        CmdPlayer player = new CmdPlayer();
        player.setAttribute(Attribute.INTELLIGENCE, 12);
        System.out.println("These are your attributes:");
        for (Attribute attr : Attribute.values()) {
            if (!attr.isHidden()) {
                System.out.printf(" %s: %d%n", attr.getName(), player.getAttribute(attr));
            }
        }
        int points = 4;
        System.out.println(System.out.format("You get %d points to increase any of them.", points));
        int selection;
        for (Attribute attr : Attribute.values()) {
            if (!attr.isHidden() && points > 0) {
                selection = select(points+1, scanner, String.format("Increase %s? (0-%d) ", attr.getName(), points));
                points -= selection;
                player.setAttribute(attr, player.getAttribute(attr)+selection);
            }
        }
        System.out.println();
        System.out.println("These are your skills:");
        for (Skill skill : Skill.values()) {
            if (!skill.isHidden()) {
                System.out.printf(" %s: %d%n", skill.getName(), player.getSkill(skill));
            }
        }
        points = 16;
        System.out.printf("You get %d points to increase any of them.%n", points);
        for (Skill skill : Skill.values()) {
            if (!skill.isHidden() && points > 0) {
                selection = select(points+1, scanner, String.format("Increase %s? (0-%d) ", skill.getName(), points));
                points -= selection;
                player.setSkill(skill, player.getSkill(skill)+selection);
            }
        }
        System.out.println();
        player.sleep();
        return player;
    }

    public static void displayInventory(final CmdPlayer player) {
        System.out.println("This is in your inventory:");
        System.out.printf(" Gold: %d%n", player.getGold());
        pause(500);
        for (Item item : player.getInventory()) {
            System.out.printf(" %s%n", item.getName());
            pause(200);
        }
    }

    public static void displayStats(CmdPlayer player) {
        System.out.println("These are your stats:");
        System.out.printf(" Day: %d%n", player.getDay());
        pause(500);
        System.out.printf(" Known Spells: %d/%d%n", player.getSpellTome().size(), Spell.values().length);
        pause(500);
        for (Bar bar : Bar.values()) {
            System.out.printf(" %s: %d/%d%n", bar.getName(), player.getCurrentBar(bar), player.getMaxBar(bar));
            pause(500);
        }
        for (Attribute attr : Attribute.values()) {
            if (!attr.isHidden()) {
                System.out.printf(" %s: %d%n", attr.getName(), player.getAttribute(attr));
                pause(500);
            }
        }
        for (Skill skill : Skill.values()) {
            if (!skill.isHidden()) {
                System.out.printf(" %s: %d%n", skill.getName(), player.getSkill(skill));
                pause(500);
            }
        }
    }

    public static void changeLocation(final CmdPlayer player, final Scanner scanner) {
        Location[] locations = Location.values();
        System.out.println("Where do you want to go?");
        pause(500);
        displayOptions(locations);
        Location location = locations[select(locations.length, scanner, "Choose a location: ")];
        if (player.checkGoTo(location)) {
            player.goTo(location);
        }
    }

    public static void meditate(final CmdPlayer player, final Scanner scanner) {
        System.out.println("Meditating...");
        List<Spell> spells = new ArrayList<>(player.getSpellTome());
        displayOptions(spells);
        final Spell selectedSpell = spells.get(select(spells.size(), scanner, "Choose a spell to meditate about: "));
        if (player.checkMeditate(selectedSpell)) {
            player.meditate(selectedSpell);
        }
    }

    public static void experiment(final CmdPlayer player, final Scanner scanner) {
        System.out.println("Experimenting...");
        List<Spell> spells = new ArrayList<>(player.getSpellTome());
        displayOptions(spells);
        final Spell selectedSpell1 = spells.get(select(spells.size(), scanner, "Choose the first spell to experiment with: "));
        final Spell selectedSpell2 = spells.get(select(spells.size(), scanner, "Choose the second spell to experiment with: "));
        if (player.checkExperiment(selectedSpell1, selectedSpell2)) {
            player.experiment(selectedSpell1, selectedSpell2);
        }
    }

    public static void enchant(final CmdPlayer player, final Scanner scanner) {
        System.out.println("Enchanting...");
        final List<UnenchantedItem> unenchantedInv = player.getInventory().stream().filter(item -> item instanceof UnenchantedItem).map(UnenchantedItem.class::cast).toList();
        if (unenchantedInv.isEmpty()) {
            System.out.println("You have no item to enchant!");
            return;
        }
        displayOptions(unenchantedInv);
        final UnenchantedItem unenchantedItem = unenchantedInv.get(select(unenchantedInv.size(), scanner, "Choose an item to enchant: "));
        final List<Spell> spells = new ArrayList<>(player.getSpellTome());
        displayOptions(spells);
        final Spell enchantment = spells.get(select(spells.size(), scanner, "Select spell: "));
        if (!player.checkEnchant(enchantment)) {
            return;
        }
        final List<Gem> gems = new LinkedList<>(player.getInventory().stream().filter(item -> item instanceof Gem && item != unenchantedItem).map(Gem.class::cast).toList());
        final List<Gem> selectedGems = new LinkedList<>();
        int neededSize = enchantment.getComplexity();
        Gem selectedGem;
        while (neededSize > 0) {
            if (gems.isEmpty()) {
                System.out.println("You don't have enough gems for this enchantment!");
                return;
            }
            displayOptions(gems);
            selectedGem = gems.get(select(gems.size(), scanner, "Select a gem: "));
            gems.remove(selectedGem);
            neededSize -= selectedGem.getSize();
            selectedGems.add(selectedGem);
        }
        player.enchant(unenchantedItem, enchantment, selectedGems);
    }

    public static void disenchant(final CmdPlayer player, final Scanner scanner) {
        System.out.println("Disenchanting...");
        final List<EnchantedItem> enchantedInv = player.getInventory().stream().filter(item -> item instanceof EnchantedItem).map(EnchantedItem.class::cast).toList();
        if (enchantedInv.isEmpty()) {
            System.out.println("You have no item to disenchant!");
            return;
        }
        displayOptions(enchantedInv);
        final EnchantedItem enchantedItem = enchantedInv.get(select(enchantedInv.size(), scanner, "Choose an item to enchant: "));
        if (player.checkDisenchant()) {
            player.disenchant(enchantedItem);
        }
    }

    public static void sleep(final CmdPlayer player) {
        System.out.println("You sleep until the next day...");
        pause(500);
        player.sleep();
    }

    public static int select(final int maxValue, final Scanner scanner, final String prompt) {
        int selection = -1;
        while (selection < 0 || selection >= maxValue) {
            System.out.print(prompt);
            try {
                selection = scanner.nextInt();
            }
            catch (InputMismatchException ex) {/* ignored */}
        }
        return selection;
    }

    public static <T extends Named> void displayOptions(T[] options) {
        displayOptions(Arrays.stream(options));
    }

    public static <T extends Named> void displayOptions(List<T> options) {
        displayOptions(options.stream());
    }

    private static <T extends Named> void displayOptions(Stream<T> stream) {
        final int[] index = new int[]{0};
        stream.forEach(named -> System.out.printf("%3d: %s%n", index[0]++, named.getName()));
    }

    private static void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {/* ignore */}
    }

}
