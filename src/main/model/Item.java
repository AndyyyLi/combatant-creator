package model;

/**
 * Item superclass covers all essential information that every type of item must have, including name, a short
 * description, and stat changes that the item gives if equipped.
 */

public class Item {
    private String itemName;
    private String itemDescription;

    private int itemHealth;
    private int itemEnergy;
    private int itemWeaponDamage;
    private int itemSpellDamage;
    private int itemDefense;
    private int itemSpeed;

    public Item(String name, String description, int health, int energy, int weaponDamage, int spellDamage,
                int defense, int speed) {
        itemName = name;
        itemDescription = description;
        itemHealth = health;
        itemEnergy = energy;
        itemWeaponDamage = weaponDamage;
        itemSpellDamage = spellDamage;
        itemDefense = defense;
        itemSpeed = speed;
    }

    // GETTER METHODS
    public String getName() {
        return itemName;
    }

    public String getDescription() {
        return itemDescription;
    }

    public int getItemHealth() {
        return itemHealth;
    }

    public int getItemEnergy() {
        return itemEnergy;
    }

    public int getItemWeaponDamage() {
        return itemWeaponDamage;
    }

    public int getItemSpellDamage() {
        return itemSpellDamage;
    }

    public int getItemDefense() {
        return itemDefense;
    }

    public int getItemSpeed() {
        return itemSpeed;
    }


    // REQUIRES: character has a name
    // MODIFIES: this
    // EFFECTS: renames item such that it follows this format: "CharacterName's ItemName"
    public void personalizeItemName(Character character) {
        // stub
    }

    // REQUIRES: item cannot be equipped
    // MODIFIES: this
    // EFFECTS: doubles all of the item's stat changes, positive and negative
    public void extremifyItem() {
        // stub
    }
}
