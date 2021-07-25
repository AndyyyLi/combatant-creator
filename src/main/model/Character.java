package model;

/**
 * Character class represents customizable character with a name, 6 unique stats, and 3 equipment slots,
 * each for a different type of equipment.
 */

public class Character {
    private String charName;

    private int totalHealth;
    private int totalEnergy;
    private int totalWeaponDamage;
    private int totalSpellDamage;
    private int totalDefense;
    private int totalSpeed;

    private Weapon currentWeapon;
    private Spell currentSpell;
    private Armour currentArmour;


    public Character() {
        charName = "Your combatant";

        totalHealth = 100;
        totalEnergy = 100;
        totalWeaponDamage = 0;
        totalSpellDamage = 0;
        totalDefense = 0;
        totalSpeed = 25;

        currentWeapon = null;
        currentSpell = null;
        currentArmour = null;
    }

    // GETTER METHODS
    public String getCharName() {
        return charName;
    }

    public int getTotalHealth() {
        return totalHealth;
    }

    public int getTotalEnergy() {
        return totalEnergy;
    }

    public int getTotalWeaponDamage() {
        return totalWeaponDamage;
    }

    public int getTotalSpellDamage() {
        return totalSpellDamage;
    }

    public int getTotalDefense() {
        return totalDefense;
    }

    public int getTotalSpeed() {
        return totalSpeed;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public Spell getCurrentSpell() {
        return currentSpell;
    }

    public Armour getCurrentArmour() {
        return currentArmour;
    }

    // REQUIRES: name must be at least 1 character long
    // MODIFIES: this
    // EFFECTS: sets character's name as given name
    public void setName(String name) {
        this.charName = name;
    }

    // MODIFIES: this
    // EFFECTS: if item can be equipped then equip item, if there already is an equipped item of that category
    //          then replace it with given item
    //          if item cannot be equipped, nothing changes
    public void equipItem(Item item) {
        if (canEquipItem(item)) {
            if (item instanceof Weapon) {
                this.currentWeapon = (Weapon) item;
            } else if (item instanceof Spell) {
                this.currentSpell = (Spell) item;
            } else {
                this.currentArmour = (Armour) item;
            }

            this.totalHealth += item.getItemHealth();
            this.totalEnergy += item.getItemEnergy();
            this.totalWeaponDamage += item.getItemWeaponDamage();
            this.totalSpellDamage += item.getItemSpellDamage();
            this.totalDefense += item.getItemDefense();
            this.totalSpeed += item.getItemSpeed();
        }
    }

    // EFFECTS: returns true only if equipping given item keeps the character's total health, energy, and speed stats
    //          greater than zero, AND keeps the character's total weapon damage, spell damage, and defense stats
    //          non-negative
    //          otherwise return false
    public boolean canEquipItem(Item item) {
        int finalHealth = this.totalHealth + item.getItemHealth();
        int finalEnergy = this.totalEnergy + item.getItemEnergy();
        int finalWeaponDmg = this.totalWeaponDamage + item.getItemWeaponDamage();
        int finalSpellDmg = this.totalSpellDamage + item.getItemSpellDamage();
        int finalDefense = this.totalDefense + item.getItemDefense();
        int finalSpeed = this.totalSpeed + item.getItemSpeed();

        return (finalHealth > 0 && finalEnergy > 0 && finalWeaponDmg >= 0 && finalSpellDmg >= 0 && finalDefense >= 0
                && finalSpeed > 0);
    }

    // REQUIRES: character has this item equipped
    // MODIFIES: this
    // EFFECTS: removes currently equipped item from character
    public void removeItem(Item item) {
        if (item instanceof Weapon) {
            this.currentWeapon = null;
        } else if (item instanceof Spell) {
            this.currentSpell = null;
        } else {
            this.currentArmour = null;
        }

        this.totalHealth -= item.getItemHealth();
        this.totalEnergy -= item.getItemEnergy();
        this.totalWeaponDamage -= item.getItemWeaponDamage();
        this.totalSpellDamage -= item.getItemSpellDamage();
        this.totalDefense -= item.getItemDefense();
        this.totalSpeed -= item.getItemSpeed();
    }

}
