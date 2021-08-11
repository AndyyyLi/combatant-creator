package model;

import exceptions.CannotRemoveItemException;
import exceptions.InvalidNameException;
import org.json.JSONObject;
import persistence.Savable;

/**
 * Character class represents customizable character with a name, 6 unique stats, and 3 equipment slots,
 * each for a different type of equipment.
 */

public class Character implements Savable {
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


    // MODIFIES: this
    // EFFECTS: sets character's name as given name
    public void setName(String name) throws InvalidNameException {
        if (name == null || name.length() == 0) {
            throw new InvalidNameException();
        }
        this.charName = name;
    }

    // MODIFIES: this
    // EFFECTS: if item can be equipped then equip item, if there already is an equipped item of that category
    //          then replace it with given item
    //          if item cannot be equipped, nothing changes
    public void equipItem(Item item) {
        if (canEquipItem(item)) {
            setItem(item);
        }
    }

    // MODIFIES: this
    // EFFECTS: equips item onto character, can only be directly called by JsonLoader
    public void setItem(Item item) {
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

    // MODIFIES: this
    // EFFECTS: removes currently equipped item from character if possible, else throws exception
    public void removeItem(Item item) throws CannotRemoveItemException {
        tryToRemove(item);

        this.totalHealth -= item.getItemHealth();
        this.totalEnergy -= item.getItemEnergy();
        this.totalWeaponDamage -= item.getItemWeaponDamage();
        this.totalSpellDamage -= item.getItemSpellDamage();
        this.totalDefense -= item.getItemDefense();
        this.totalSpeed -= item.getItemSpeed();
    }

    // MODIFIES: this
    // EFFECTS: if given item is equipped, remove the item from character, else throw exception
    public void tryToRemove(Item item) throws CannotRemoveItemException {
        if (item instanceof Weapon) {
            if (this.currentWeapon == null || !this.currentWeapon.getName().equals(item.getName())) {
                throw new CannotRemoveItemException();
            } else {
                this.currentWeapon = null;
            }
        } else if (item instanceof Spell) {
            if (this.currentSpell == null || !this.currentSpell.getName().equals(item.getName())) {
                throw new CannotRemoveItemException();
            } else {
                this.currentSpell = null;
            }
        } else {
            if (this.currentArmour == null || !this.currentArmour.getName().equals(item.getName())) {
                throw new CannotRemoveItemException();
            } else {
                this.currentArmour = null;
            }
        }
    }

    // EFFECTS: creates JSON Object out of character and equipped items
    @Override
    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        json.put("name", getCharName());
        saveWeapon(json);
        saveSpell(json);
        saveArmour(json);

        return json;
    }

    // EFFECTS: adds equipped weapon to JSON Object, adds default values if none equipped
    public void saveWeapon(JSONObject json) {
        if (getCurrentWeapon() == null) {
            json.put("weapon", "None");
        } else {
            json.put("weapon", getCurrentWeapon().getName());
            json.put("weaponAdjust", getCurrentWeapon().getExtremifyCount());
            json.put("weaponEffect", getCurrentWeapon().getHitEffect());
            json.put("weaponIntensity", getCurrentWeapon().getEffectIntensity());
        }
    }

    // EFFECTS: adds equipped spell to JSON Object, adds default values if none equipped
    public void saveSpell(JSONObject json) {
        if (getCurrentSpell() == null) {
            json.put("spell", "None");
        } else {
            json.put("spell", getCurrentSpell().getName());
            json.put("spellAdjust", getCurrentSpell().getExtremifyCount());
            json.put("spellElement", getCurrentSpell().getElement());
        }
    }

    // EFFECTS: adds equipped armour to JSON Object, adds default values if none equipped
    public void saveArmour(JSONObject json) {
        if (getCurrentArmour() == null) {
            json.put("armour", "None");
        } else {
            json.put("armour", getCurrentArmour().getName());
            json.put("armourAdjust", getCurrentArmour().getExtremifyCount());
            json.put("armourMaterial", getCurrentArmour().getMaterial());
            json.put("armourAbility", getCurrentArmour().getDefensiveAbility());
        }
    }
}
