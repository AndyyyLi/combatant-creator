package ui;

import exceptions.CannotRemoveItemException;
import exceptions.InvalidNameException;
import model.*;
import model.Character;
import org.json.JSONException;
import persistence.JsonLoader;
import persistence.JsonSaver;

// All usages of Scanner and input based on TellerApp
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Combatant Creator Application
 */

public class CombatantCreator {

    private static final String JSON_FILE = "./data/character.json";
    private Character character;
    private WeaponList weaponList;
    private SpellList spellList;
    private ArmourList armourList;
    private Scanner input;
    private JsonSaver jsonSaver;
    private JsonLoader jsonLoader;

    private Category currentCategory;

    private boolean weaponsAreArranged = false;
    private boolean spellsAreArranged = false;
    private boolean armoursAreArranged = false;


    // Constructor, runCreator, initialize, print and display methods all based on TellerApp

    // exception thrown in constructor based on JsonSerializationDemo
    // EFFECTS: runs Combatant Creator application
    public CombatantCreator() throws FileNotFoundException {
        runCreator();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runCreator() {
        boolean appRunning = true;
        String select = null;

        initialize();

        currentCategory = weaponList;

        System.out.println("Welcome to Combatant Creator!");

        pickNewOrSavedCharacter();

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

        System.out.println("\n" + character.getCharName() + " is ready for battle! Thank you for playing!");
    }

    // MODIFIES: this
    // EFFECTS: initializes a new character and all item lists
    public void initialize() {
        character = new Character();
        weaponList = new WeaponList();
        spellList = new SpellList();
        armourList = new ArmourList();

        input = new Scanner(System.in);

        jsonSaver = new JsonSaver(JSON_FILE);
        jsonLoader = new JsonLoader(JSON_FILE);
    }

    public void pickNewOrSavedCharacter() {
        System.out.println("c: Create new character");
        System.out.println("l: Load saved character");
        String choice = input.next().toLowerCase();

        while (!(choice.equals("c") || choice.equals("l"))) {
            invalidInput();
            choice = input.next().toLowerCase();
        }

        if (choice.equals("l")) {
            loadCharacter();
        }

        // input "c" ends this method and starts app with a new character
    }

    // EFFECTS: shows list of items of current category to user
    public void displayCurrentCategory(Category c) {
        if (c instanceof WeaponList) {
            System.out.println("\nCurrent Category: Weapons - Select by number");
            printItemList(c);
            printArrangeOptions(c);
        } else if (c instanceof SpellList) {
            System.out.println("\nCurrent Category: Spells - Select by number");
            printItemList(c);
            printArrangeOptions(c);
        } else {
            System.out.println("\nCurrent Category: list Armours - Select by number");
            printItemList(c);
            printArrangeOptions(c);
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

    // EFFECTS: print arrangement options based on category
    public void printArrangeOptions(Category c) {
        if (c instanceof WeaponList) {
            if (!weaponsAreArranged) {
                System.out.println("\narrange: Arrange list by highest weapon damage");
            } else {
                System.out.println("\narrange: Arrange list by default");
            }
        } else if (c instanceof SpellList) {
            if (!spellsAreArranged) {
                System.out.println("\narrange: Arrange list by highest spell damage");
            } else {
                System.out.println("\narrange: Arrange list by default");
            }
        } else {
            if (!armoursAreArranged) {
                System.out.println("\narrange: Arrange list by highest defense");
            } else {
                System.out.println("\narrange: Arrange list by default");
            }
        }
    }

    // EFFECTS: print options to name character, switch category, or finish character
    public void printOtherOptions() {
        System.out.println("name: Change your character's name");
        System.out.println("switch: Switch current category");
        System.out.println("summary: See your character's name, equipment, and stats");
        System.out.println("save: Save your current character to file");
        System.out.println("finish: Finish character creation");
    }

    // Input processing methods involving chain if-else statements based on TellerApp

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
        } else if (select.equals("save")) {
            saveCharacter();
        } else if (select.equals("summary")) {
            printCharacterSummary();
        } else {
            invalidInput();
        }
    }

    // MODIFIES: this and item list
    // EFFECTS: arranges category based on category type
    public void arrangeCategory() {
        if (currentCategory instanceof WeaponList) {
            weaponsAreArranged = arrange(weaponsAreArranged);
        } else if (currentCategory instanceof SpellList) {
            spellsAreArranged = arrange(spellsAreArranged);
        } else {
            armoursAreArranged = arrange(armoursAreArranged);
        }
        System.out.println("\nArranging category...");
    }

    // MODIFIES: this and item list
    // EFFECTS: arranges category based on whether it is arranged already or not and returns arranged boolean
    public boolean arrange(boolean areArranged) {
        if (areArranged) {
            if (currentCategory instanceof WeaponList) {
                ((WeaponList) currentCategory).arrangeWeaponsByDefault();
            } else if (currentCategory instanceof SpellList) {
                ((SpellList) currentCategory).arrangeSpellsByDefault();
            } else {
                ((ArmourList) currentCategory).arrangeArmoursByDefault();
            }
            areArranged = false;
        } else {
            if (currentCategory instanceof WeaponList) {
                ((WeaponList) currentCategory).arrangeByHighestWeaponDamage();
            } else if (currentCategory instanceof SpellList) {
                ((SpellList) currentCategory).arrangeByHighestSpellDamage();
            } else {
                ((ArmourList) currentCategory).arrangeByHighestDefense();
            }
            areArranged = true;
        }
        return areArranged;
    }

    // MODIFIES: this and character
    // EFFECTS: shows current name and prompts user to rename their character
    public void pickName() {
        System.out.println("\nCurrent name: " + character.getCharName());

        System.out.println("Enter new name: (1 word at a time!)");
        String name = input.next();

        while (name.length() == 0) {
            System.out.println("\nName must be at least one character long!");
            name = input.next();
        }

        try {
            character.setName(name);
        } catch (InvalidNameException e) {
            System.err.println("Name setting error.");
        }

        continuePickingName();

        System.out.println("\nSuccess! Your character is now named " + character.getCharName());
    }

    // MODIFIES: this and character
    // EFFECTS: prompts user to name character with multiple words
    public void continuePickingName() {
        boolean keepNaming = true;
        String currentName = character.getCharName();

        while (keepNaming) {
            System.out.println("Does your name contain another word? (Yes or No)");
            String nextWord = input.next().toLowerCase();

            while (!(nextWord.equals("yes") || nextWord.equals("no"))) {
                invalidInput();
                nextWord = input.next().toLowerCase();
            }

            if (nextWord.equals("yes")) {
                inputNextWord(currentName);
                currentName = character.getCharName();
            } else {
                keepNaming = false;
            }
        }
    }

    public void inputNextWord(String currentName) {
        System.out.println("Enter next part of name:");
        String nextWord = input.next();

        while (nextWord.length() == 0) {
            System.out.println("\nWord must be at least one character long!");
            nextWord = input.next();
        }

        try {
            character.setName(currentName + " " + nextWord);
        } catch (InvalidNameException e) {
            System.err.println("Name setting error.");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to switch to a different category
    public void switchCategory() {
        System.out.println("\nChoose a category by number:");
        System.out.println("\t1. Weapons");
        System.out.println("\t2. Spells");
        System.out.println("\t3. Armours");

        String newCategory = input.next().toLowerCase();

        // While loops to allow for another input after an invalid one based on TellerApp
        while (!(newCategory.equals("1") || newCategory.equals("2") || newCategory.equals("3"))) {
            invalidInput();
            newCategory = input.next().toLowerCase();
        }

        if (newCategory.equals("1")) {
            currentCategory = weaponList;
        } else if (newCategory.equals("2")) {
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
        System.out.println("\n" + item.getName() + "'s description:");
        System.out.println(item.getDescription());
        System.out.println("\nStat changes:");
        System.out.println("Health: " + item.getItemHealth());
        System.out.println("Energy: " + item.getItemEnergy());
        System.out.println("Weapon Damage: " + item.getItemWeaponDamage());
        System.out.println("Spell Damage: " + item.getItemSpellDamage());
        System.out.println("Defense: " + item.getItemDefense());
        System.out.println("Speed: " + item.getItemSpeed() + "\n");
        if (item instanceof Weapon) {
            System.out.println(item.getName() + " can also have a hit effect.");
        } else if (item instanceof Spell) {
            System.out.println(item.getName() + " can also have an elemental enchantment.");
        } else {
            System.out.println(item.getName() + " can also change its material and have a defensive ability.");
        }
    }

    // MODIFIES: this and item
    // EFFECTS: extremifies or reverts item
    public void adjustItem(Item item) {
        System.out.println("\nHow would you like to adjust " + item.getName() + "?");
        System.out.println("\textremify: Extremify item, doubling all of its stats, positive and negative");
        System.out.println("\trevert: If item has been extremified, reverts item's stats to original");
        System.out.println("\tnothing: Go back to item options");

        String action = input.next().toLowerCase();

        while (!(action.equals("extremify") || action.equals("revert") || action.equals("nothing"))) {
            invalidInput();
            action = input.next().toLowerCase();
        }

        if (action.equals("extremify")) {
            extremify(item);
            adjustItem(item);
        } else if (action.equals("revert")) {
            revert(item);
            adjustItem(item);
        }

        // "nothing" ends the method and returns to item options
    }

    // MODIFIES: item
    // EFFECTS: extremifies item if not equipped
    public void extremify(Item item) {
        if (itemIsEquipped(item)) {
            System.out.println("\nItem is equipped, cannot extremify!");
        } else {
            item.extremifyItem();
            System.out.println("\n" + item.getName() + " has been extremified! Extremify count: "
                    + item.getExtremifyCount());
        }
    }

    // MODIFIES: item
    // EFFECTS: reverts item if not equipped
    public void revert(Item item) {
        if (itemIsEquipped(item)) {
            System.out.println("\nItem is equipped, cannot revert!");
        } else if (item.getExtremifyCount() == 0) {
            System.out.println("\nItem is not extremified, nothing to revert!");
        } else {
            item.revertItem();
            System.out.println("\n" + item.getName() + " has been reverted!");
        }
    }

    // EFFECTS: returns true if given item is currently equipped on character, false otherwise
    public boolean itemIsEquipped(Item item) {
        boolean equippedAsWeapon = (character.getCurrentWeapon() == item);
        boolean equippedAsSpell = (character.getCurrentSpell() == item);
        boolean equippedAsArmour = (character.getCurrentArmour() == item);

        return (equippedAsWeapon || equippedAsSpell || equippedAsArmour);
    }

    // MODIFIES: this and character
    // EFFECTS: equips item onto character in the proper category slot if possible
    public void equipSelectedItem(Item item) {
        if (character.canEquipItem(item)) {
            if (item instanceof Weapon) {
                printReplacement(item);
                pickHitEffectAndIntensity((Weapon) item);
            } else if (item instanceof Spell) {
                printReplacement(item);
                pickElement((Spell) item);
            } else {
                printReplacement(item);
                pickMaterialAndAbility((Armour) item);
            }
            character.equipItem(item);
            System.out.println(item.getName() + " has been successfully equipped!");
        } else {
            System.out.println("Cannot equip item, its stats are not compatible with character's current stats.");
        }
    }

    // EFFECTS: prints the replacement message of items
    public void printReplacement(Item item) {
        if (item instanceof Weapon) {
            Weapon prevWeapon = character.getCurrentWeapon();

            if (!(prevWeapon == null)) {
                System.out.println("\nReplacing " + prevWeapon.getName() + " with " + item.getName() + "!");
            }
        } else if (item instanceof Spell) {
            Spell prevSpell = character.getCurrentSpell();

            if (!(prevSpell == null)) {
                System.out.println("\nReplacing " + prevSpell.getName() + " with " + item.getName() + "!");
            }
        } else {
            Armour prevArmour = character.getCurrentArmour();

            if (!(prevArmour == null)) {
                System.out.println("\nReplacing " + prevArmour.getName() + " with " + item.getName() + "!");
            }
        }
    }

    // MODIFIES: this and weapon
    // EFFECTS: selects weapon hit effect and intensity
    public void pickHitEffectAndIntensity(Weapon weapon) {
        System.out.println("\nAll weapons can have an occasional hit effect along with an intensity level, whereby"
                + " increasing the level strengthens the effect but lowers the chances of it occurring.");
        System.out.println("Intensity levels range between 1-5 (inclusive) except for No Effect which is always 0.");
        System.out.println("Read each option carefully and select the one you would like by its number.");
        System.out.println("\t0. No Effect (default)");
        System.out.println("\t1. Stun: Temporarily stuns target, preventing them from moving or attacking. Intensity"
                + " level indicates how many seconds the target is stunned for");
        System.out.println("\t2. Armour Break: Temporarily removes a portion of the target's armour. Intensity level"
                + " indicates how much of the target's defense is temporarily removed, with 1 = 10% and 5 = 50%");
        System.out.println("\t3. Pierce: Hit multiple targets at once. Intensity level indicates how many additional"
                + " targets are hit at once");
        System.out.println("\t4. Multihit: Hit target multiple times in a row. Intensity level indicates how many"
                + " additional times the target is hit");

        setWeaponEffect(weapon);

        if (weapon.getHitEffect().equals("No Effect")) {
            weapon.setEffectIntensity(0);
        } else {
            System.out.println("Please select the intensity of " + weapon.getHitEffect() + ".");
            setWeaponEffectIntensity(weapon);
            System.out.println(weapon.getName() + " now has " + weapon.getHitEffect() + " of intensity level "
                    + weapon.getEffectIntensity() + "!");
        }
    }

    // MODIFIES: this and weapon
    // EFFECTS: sets weapon's hit effect
    public void setWeaponEffect(Weapon weapon) {
        String effect = input.next();

        while (!(effect.equals("0") || effect.equals("1") || effect.equals("2") || effect.equals("3")
                || effect.equals("4"))) {
            invalidInput();
            effect = input.next();
        }

        if (effect.equals("0")) {
            weapon.applyHitEffect("No Effect");
        } else if (effect.equals("1")) {
            weapon.applyHitEffect("Stun");
        } else if (effect.equals("2")) {
            weapon.applyHitEffect("Armour Break");
        } else if (effect.equals("3")) {
            weapon.applyHitEffect("Pierce");
        } else {
            weapon.applyHitEffect("Multihit");
        }
    }

    // MODIFIES: this and weapon
    // EFFECTS: sets weapon's hit effect intensity level
    public void setWeaponEffectIntensity(Weapon weapon) {
        String intensity = input.next();

        while (!(intensity.equals("1") || intensity.equals("2") || intensity.equals("3") || intensity.equals("4")
                || intensity.equals("5"))) {
            System.out.println("Number must be between 1 and 5 (inclusive)!");
            intensity = input.next();
        }

        weapon.setEffectIntensity(Integer.parseInt(intensity));
    }

    // MODIFIES: this and spell
    // EFFECTS: selects spell element
    public void pickElement(Spell spell) {
        System.out.println("\nAll spells can be enchanted with an element of nature, which allows the spells to"
                + " inflict unique additional effects.");
        System.out.println("Read each option carefully and select the one you would like by its name.");
        System.out.println("\t0. Normal (default)");
        System.out.println("\t1. Fire: Burns targets hit, dealing damage based on their health over 5 seconds");
        System.out.println("\t2. Water: Drenches targets hit, reducing their speed for 5 seconds");
        System.out.println("\t3. Ground: Creates tremors beneath targets hit, preventing them from attacking for"
                + " 3 seconds");

        setElement(spell);
    }

    // MODIFIES: this and spell
    // EFFECTS: sets element of spell
    public void setElement(Spell spell) {
        String element = input.next();

        while (!(element.equals("0") || element.equals("1") || element.equals("2") || element.equals("3"))) {
            invalidInput();
            element = input.next();
        }

        if (element.equals("0")) {
            spell.pickElement("Normal");
        } else if (element.equals("1")) {
            spell.pickElement("Fire");
        } else if (element.equals("2")) {
            spell.pickElement("Water");
        } else {
            spell.pickElement("Ground");
        }

        if (!spell.getElement().equals("Normal")) {
            System.out.println(spell.getName() + " has been enchanted with the element of "
                    + spell.getElement() + "!");
        }
    }

    // MODIFIES: this and armour
    // EFFECTS: selects armour material and defensive ability
    public void pickMaterialAndAbility(Armour armour) {
        System.out.println("\nAll armours can change their material and obtain a defensive ability, which occasionally"
                + " activates upon taking damage.");
        System.out.println("Read each material option carefully and select the one you would like by its number.");
        System.out.println("\t1. Super Steel (default): Simple, sturdy, strong");
        System.out.println("\t2. Ice Diamond: Reflective, sleek, vibrant");
        System.out.println("\t3. Magma Obsidian: Molten, indestructible, bulky");

        setArmourMaterial(armour);

        System.out.println("\nRead each defensive ability carefully and select the one you would like by its number.");
        System.out.println("\t0. No Ability (default)");
        System.out.println("\t1. Rapid Regen: Immediately heal for 100 health");
        System.out.println("\t2. Shockback: Temporarily paralyzes all nearby enemies");
        System.out.println("\t3. Nimble Rush: Temporarily doubles speed");

        setArmourDefensiveAbility(armour);

        if (armour.getDefensiveAbility().equals("No Ability")) {
            System.out.println(armour.getName() + " is made of " + armour.getMaterial() + "!");
        } else {
            System.out.println(armour.getName() + " is made of " + armour.getMaterial() + " and has the "
                    + armour.getDefensiveAbility() + " ability!");
        }
    }

    // MODIFIES: this and armour
    // EFFECTS: sets armour's material
    public void setArmourMaterial(Armour armour) {
        String material = input.next();

        while (!(material.equals("1") || material.equals("2") || material.equals("3"))) {
            invalidInput();
            material = input.next();
        }

        if (material.equals("1")) {
            armour.changeMaterial("Super Steel");
        } else if (material.equals("2")) {
            armour.changeMaterial("Ice Diamond");
        } else {
            armour.changeMaterial("Magma Obsidian");
        }
    }

    // MODIFIES: this and armour
    // EFFECTS: sets armour's defensive ability
    public void setArmourDefensiveAbility(Armour armour) {
        String ability = input.next();

        while (!(ability.equals("0") || ability.equals("1") || ability.equals("2") || ability.equals("3"))) {
            invalidInput();
            ability = input.next();
        }

        if (ability.equals("0")) {
            armour.changeDefensiveAbility("No Ability");
        } else if (ability.equals("1")) {
            armour.changeDefensiveAbility("Rapid Regen");
        } else if (ability.equals("2")) {
            armour.changeDefensiveAbility("Shockback");
        } else {
            armour.changeDefensiveAbility("Nimble Rush");
        }
    }

    // MODIFIES: this and character
    // EFFECTS: removes item from character if equipped
    public void removeSelectedItem(Item item) {
        System.out.println("\n");
        try {
            character.removeItem(item);
            System.out.println(item.getName() + " has been removed!");
        } catch (CannotRemoveItemException e) {
            System.err.println(item.getName() + " is not equipped, cannot remove!");
        }
        itemOptions(item);
    }

    // EFFECTS: prints character's name, equipped items, and total stats to screen
    public void printCharacterSummary() {
        System.out.println("\n" + character.getCharName() + "'s Summary:");
        if (character.getCurrentWeapon() == null) {
            System.out.println("No Weapon Equipped");
        } else {
            System.out.println("\nWeapon: " + character.getCurrentWeapon().getName() + " with "
                    + character.getCurrentWeapon().getHitEffect() + " ("
                    + character.getCurrentWeapon().getEffectIntensity() + ")");
        }
        if (character.getCurrentSpell() == null) {
            System.out.println("No Spell Equipped");
        } else {
            System.out.println("Spell: " + character.getCurrentSpell().getName() + " with the element of "
                    + character.getCurrentSpell().getElement());
        }
        if (character.getCurrentArmour() == null) {
            System.out.println("No Armour Equipped");
        } else {
            System.out.println("Armour: " + character.getCurrentArmour().getMaterial()
                    + " " + character.getCurrentArmour().getName() + " with "
                    + character.getCurrentArmour().getDefensiveAbility());
        }

        printStats();
    }

    // EFFECTS: prints character's total stats
    public void printStats() {
        System.out.println("\nHealth: " + character.getTotalHealth());
        System.out.println("Energy: " + character.getTotalEnergy());
        System.out.println("Weapon Damage: " + character.getTotalWeaponDamage());
        System.out.println("Spell Damage: " + character.getTotalSpellDamage());
        System.out.println("Defense: " + character.getTotalDefense());
        System.out.println("Speed: " + character.getTotalSpeed());
    }

    // save and load character methods based on JsonSerializationDemo

    // EFFECTS: saves character to file
    public void saveCharacter() {
        try {
            jsonSaver.open();
            jsonSaver.save(character);
            jsonSaver.close();
            System.out.println(character.getCharName() + " has been saved!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save to file: " + JSON_FILE);
        }
    }

    // MODIFIES: this and character
    // EFFECTS: loads character from file
    public void loadCharacter() {
        try {
            character = jsonLoader.load();
            System.out.println(character.getCharName() + " is back for some changes!");
        } catch (IOException e) {
            System.out.println("Load error from file: " + JSON_FILE);
        } catch (JSONException e) {
            System.out.println("No character saved! Creating new character...");
        } catch (InvalidNameException e) {
            System.err.println("Saved character has no name! Creating new character...");
        }
    }

    // EFFECTS: prints an error statement notifying user that their input was not valid
    public void invalidInput() {
        System.err.println("\nUh oh, your input is not valid! Please try again.");
    }
}
