package ui;

import model.*;
import model.Character;

// All usages of Scanner and input based on TellerApp UI class
import java.util.Scanner;

/**
 * Combatant Creator Application
 */

public class CombatantCreator {

    private Character character;
    private WeaponList weaponList;
    private SpellList spellList;
    private ArmourList armourList;
    private Scanner input;

    private Category currentCategory;

    private boolean categoryIsArranged = false;

    // EFFECTS: runs Combatant Creator application
    public CombatantCreator() {
        runCreator();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runCreator() {
        boolean appRunning = true;
        String select = null;

        initialize();

        currentCategory = weaponList;

        while (appRunning) {
            displayCurrentCategory(currentCategory);
            printOtherOptions();
            select = input.next();
            select = select.toLowerCase();

            if (select.equals("finish")) {
                appRunning = false;
            } else {
                processSelection(select);
            }
        }

        completeCharacter();
    }

    // MODIFIES: this
    // EFFECTS: initializes a new character and all item lists
    public void initialize() {
        character = new Character();
        weaponList = new WeaponList();
        spellList = new SpellList();
        armourList = new ArmourList();

        input = new Scanner(System.in);
    }

    // EFFECTS: shows list of items of current category to user
    public void displayCurrentCategory(Category c) {
        if (c instanceof WeaponList) {
            System.out.println("\nCurrent Category: Weapons - Select by number");
            printItemList(c);
            if (!categoryIsArranged) {
                System.out.println("arrange: Arrange list by highest weapon damage");
            }
        } else if (c instanceof SpellList) {
            System.out.println("\nCurrent Category: Spells - Select by number");
            printItemList(c);
            if (!categoryIsArranged) {
                System.out.println("arrange: Arrange list by highest spell damage");
            }
        } else {
            System.out.println("\nCurrent Category: list Armours - Select by number");
            printItemList(c);
            if (!categoryIsArranged) {
                System.out.println("arrange: Arrange list by highest defense");
            }
        }
        if (categoryIsArranged) {
            System.out.println("arrange: Arrange list by default");
        }
    }

    // EFFECTS: prints item list of given category
    public void printItemList(Category c) {
        int count = 1;
        for (Item item : c.getItemList()) {
            System.out.println("\t" + count + ". " + item.getName());
            count++;
        }
    }

    // EFFECTS: print options to name character, switch category, or finish character
    public void printOtherOptions() {
        System.out.println("\nname: Change your character's name");
        System.out.println("switch: Switch current category");
        System.out.println("summary: See your character's name, equipment, and stats");
        System.out.println("finish: Finish character creation");
    }

    // MODIFIES: this
    // EFFECTS: processes user's selection of an item or category
    public void processSelection(String select) {
        if (select.equals("1")) {
            System.out.println("\n" + currentCategory.selectItem(1).getName());
            itemOptions(currentCategory.selectItem(1));
        } else if (select.equals("2")) {
            System.out.println("\n" + currentCategory.selectItem(2).getName());
            itemOptions(currentCategory.selectItem(2));
        } else if (select.equals("3")) {
            System.out.println("\n" + currentCategory.selectItem(3).getName());
            itemOptions(currentCategory.selectItem(3));
        } else if (select.equals("arrange")) {
            arrangeCategory();
        } else if (select.equals("name")) {
            pickName();
        } else if (select.equals("switch")) {
            switchCategory();
        } else if (select.equals("summary")) {
            printCharacterSummary();
        } else {
            invalidInput();
        }
    }

    // MODIFIES: item list
    // EFFECTS: arranges list accordingly based on list type
    public void arrangeCategory() {
        if (!categoryIsArranged) {
            if (currentCategory instanceof WeaponList) {
                ((WeaponList) currentCategory).arrangeByHighestWeaponDamage();
            } else if (currentCategory instanceof SpellList) {
                ((SpellList) currentCategory).arrangeByHighestSpellDamage();
            } else {
                ((ArmourList) currentCategory).arrangeByHighestDefense();
            }
            categoryIsArranged = true;
        } else {
            if (currentCategory instanceof WeaponList) {
                ((WeaponList) currentCategory).arrangeWeaponsByDefault();
            } else if (currentCategory instanceof SpellList) {
                ((SpellList) currentCategory).arrangeSpellsByDefault();
            } else {
                ((ArmourList) currentCategory).arrangeArmoursByDefault();
            }
            categoryIsArranged = false;
        }
        System.out.println("Arranging list...");
    }

    // MODIFIES: this and character
    // EFFECTS: shows current name and prompts user to rename their character
    public void pickName() {
        System.out.println("\nCurrent name: " + character.getCharName());
        System.out.println("Enter new name");
        String newName = input.next();

        while (newName.length() == 0) {
            System.out.println("\nName must be at least one character long!");
            newName = input.next();
        }

        character.setName(newName);
        System.out.println("\nSuccess! Your character is now named " + character.getCharName());
    }

    // MODIFIES: this
    // EFFECTS: prompts user to switch to a different category
    public void switchCategory() {
        System.out.println("\nChoose a category by name:");
        System.out.println("\t1. Weapons");
        System.out.println("\t2. Spells");
        System.out.println("\t3. Armours");

        String newCategory = input.next().toLowerCase();

        while (!(newCategory.equals("weapons") || newCategory.equals("spells") || newCategory.equals("armours"))) {
            invalidInput();
            newCategory = input.next().toLowerCase();
        }

        if (newCategory.equals("weapons")) {
            currentCategory = weaponList;
        } else if (newCategory.equals("spells")) {
            currentCategory = spellList;
        } else {
            currentCategory = armourList;
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select an option regarding item
    public void itemOptions(Item item) {
        System.out.println("\nWhat would you like to do with " + item.getName() + "?");
        System.out.println("\tequip: Equip item");
        System.out.println("\tinfo: Get item's details");
        System.out.println("\tadjust: Adjust stats");
        System.out.println("\tremove: Remove item");
        System.out.println("\tback: Go back to list");

        String action = input.next().toLowerCase();

        while (!(action.equals("equip") || action.equals("info") || action.equals("adjust")
                || action.equals("remove") || action.equals("back"))) {
            invalidInput();
            action = input.next().toLowerCase();
        }

        if (action.equals("equip")) {
            equipSelectedItem(item);
        } else if (action.equals("info")) {
            itemInfo(item);
            itemOptions(item);
        } else if (action.equals("adjust")) {
            adjustItem(item);
            itemOptions(item);
        } else if (action.equals("remove")) {
            removeSelectedItem(item);
        }

        // "back" ends the method and returns to category display
    }

    // EFFECTS: shows item description, stat changes, and what special features the item can have
    public void itemInfo(Item item) {
        System.out.println("\n" + item.getName() + "description:");
        System.out.println(item.getDescription());
        System.out.println("\nStat changes:");
        System.out.println("Health: " + item.getItemHealth());
        System.out.println("Energy: " + item.getItemEnergy());
        System.out.println("Weapon Damage: " + item.getItemWeaponDamage());
        System.out.println("Spell Damage: " + item.getItemSpellDamage());
        System.out.println("Defense: " + item.getItemDefense());
        System.out.println("Speed: " + item.getItemSpeed() + "\n");
        if (item instanceof Weapon) {
            System.out.println(item.getName() + " can optionally have an occasional hit effect.");
        } else if (item instanceof Spell) {
            System.out.println(item.getName() + " can optionally have an elemental enchantment.");
        } else {
            System.out.println(item.getName() + " can optionally change its material and have a defensive ability"
                    + "that occasionally activates upon taking damage.");
        }
    }

    // MODIFIES: this and item
    // EFFECTS: extremifies or reverts item
    public void adjustItem(Item item) {
        System.out.println("\nHow would you like to adjust " + item.getName() + "?");
        System.out.println("\textremify: Extremify item, doubling all of its stats!");
        System.out.println("\trevert: If item has been extremified, reverts item's stats to original.");
        System.out.println("\tnothing: Go back to item options.");

        String action = input.next().toLowerCase();

        while (!(action.equals("extremify") || action.equals("revert") || action.equals("nothing"))) {
            invalidInput();
            action = input.next().toLowerCase();
        }

        if (action.equals("extremify")) {
            item.extremifyItem();
            System.out.println(item.getName() + " has been extremified! Extremify count: " + item.getExtremifyCount());
            adjustItem(item);
        } else if (action.equals("revert")) {
            if (item.getExtremifyCount() == 0) {
                System.out.println("Item is not extremified!");
            } else {
                item.revertItem();
                System.out.println(item.getName() + " has been reverted!");
            }
            adjustItem(item);
        }

        // "nothing" ends the method and returns to item options
    }

    // MODIFIES: this and character
    // EFFECTS: equips item onto character in the proper category slot
    public void equipSelectedItem(Item item) {
        System.out.println("\n");
        if (item instanceof Weapon) {
            Weapon prevWeapon = character.getCurrentWeapon();
            if (!(prevWeapon == null)) {
                System.out.println("Replacing " + prevWeapon.getName() + " with " + item.getName() + "!");
            }
            pickHitEffectAndIntensity((Weapon) item);
        } else if (item instanceof Spell) {
            Spell prevSpell = character.getCurrentSpell();
            if (!(prevSpell == null)) {
                System.out.println("Replacing " + prevSpell.getName() + " with " + item.getName() + "!");
            }
            pickElement((Spell) item);
        } else {
            Armour prevArmour = character.getCurrentArmour();
            if (!(prevArmour == null)) {
                System.out.println("Replacing " + prevArmour.getName() + " with " + item.getName() + "!");
            }
            pickMaterialAndAbility((Armour) item);
        }
        character.equipItem(item);
        System.out.println(item.getName() + " has been successfully equipped!");
    }

    // MODIFIES: weapon
    // EFFECTS: selects weapon hit effect and intensity
    public void pickHitEffectAndIntensity(Weapon weapon) {

    }

    // MODIFIES: spell
    // EFFECTS: selects spell element
    public void pickElement(Spell spell) {

    }

    // MODIFIES: armour
    // EFFECTS: selects armour material and defensive ability
    public void pickMaterialAndAbility(Armour armour) {

    }

    // MODIFIES: this and character
    // EFFECTS: removes item from character if equipped
    public void removeSelectedItem(Item item) {
        System.out.println("\n");
        if (character.getCurrentWeapon() == item || character.getCurrentSpell() == item
                || character.getCurrentArmour() == item) {
            character.removeItem(item);
            System.out.println(item.getName() + " has been removed!");
        } else {
            System.out.println(item.getName() + " is not equipped, cannot remove!");
            itemInfo(item);
        }
    }

    // EFFECTS: prints character's name, equipped items, and total stats to screen
    public void printCharacterSummary() {
        System.out.println("\n" + character.getCharName() + "'s Summary:");
        System.out.println("\nWeapon: " + character.getCurrentWeapon().getName());
        System.out.println("Spell: " + character.getCurrentSpell().getName());
        System.out.println("Armour: " + character.getCurrentArmour().getName());
        System.out.println("Health: " + character.getTotalHealth());
        System.out.println("Energy: " + character.getTotalEnergy());
        System.out.println("Weapon Damage: " + character.getTotalWeaponDamage());
        System.out.println("Spell Damage: " + character.getTotalSpellDamage());
        System.out.println("Defense: " + character.getTotalDefense());
        System.out.println("Speed: " + character.getTotalSpeed());
    }

    // EFFECTS: announces that user's character is done being customized and is ready for battle!
    public void completeCharacter() {

    }

    public void invalidInput() {
        System.err.println("\nUh oh, your input is not valid! Please try again.");
    }

}
