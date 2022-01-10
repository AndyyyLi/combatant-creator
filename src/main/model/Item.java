package model;

import javax.swing.*;

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
    private ImageIcon itemImg;

    private int extremifyCount;

    public Item(String name, String description, int health, int energy, int weaponDamage, int spellDamage,
                int defense, int speed, ImageIcon img) {
        itemName = name;
        itemDescription = description;
        itemHealth = health;
        itemEnergy = energy;
        itemWeaponDamage = weaponDamage;
        itemSpellDamage = spellDamage;
        itemDefense = defense;
        itemSpeed = speed;
        itemImg = img;
        extremifyCount = 0;
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

    public int getExtremifyCount() {
        return extremifyCount;
    }

    public ImageIcon getItemIcon() {
        return itemImg;
    }

    // REQUIRES: item cannot be equipped
    // MODIFIES: this
    // EFFECTS: doubles all of the item's stat changes, positive and negative
    public void extremifyItem() {
        this.itemHealth *= 2;
        this.itemEnergy *= 2;
        this.itemWeaponDamage *= 2;
        this.itemSpellDamage *= 2;
        this.itemDefense *= 2;
        this.itemSpeed *= 2;
        this.extremifyCount++;
    }

    // REQUIRES: item cannot be equipped and must have been extremified at least once
    // MODIFIES: this
    // EFFECTS: reverts all of the item's stat changes to their original values
    public void revertItem() {
        while (extremifyCount > 0) {
            this.itemHealth /= 2;
            this.itemEnergy /= 2;
            this.itemWeaponDamage /= 2;
            this.itemSpellDamage /= 2;
            this.itemDefense /= 2;
            this.itemSpeed /= 2;
            this.extremifyCount--;
        }
    }
}
