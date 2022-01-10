package model;

import javax.swing.*;

/**
 * Armour is a type of Item which mainly provides stat changes to health, defense, and speed, and also include
 * exclusive options to choose the armour's material and defensive ability when attacked
 */

public class Armour extends Item {

    // material can be Super Steel (default), Ice Diamond, or Magma Obsidian
    private String material;

    // defensive ability can be None (default), Rapid Regen, Shockback, or Nimble Rush
    private String defensiveAbility;

    public Armour(String name, String description, int health, int energy, int weaponDamage, int spellDamage,
                  int defense, int speed, ImageIcon img) {

        super(name, description, health, energy, weaponDamage, spellDamage, defense, speed, img);
        material = "Super Steel";
        defensiveAbility = "None";

    }

    // GETTER METHODS
    public String getMaterial() {
        return material;
    }

    public String getDefensiveAbility() {
        return defensiveAbility;
    }


    // REQUIRES: material name is one of: Super Steel, Ice Diamond, Magma Obsidian
    // MODIFIES: this
    // EFFECTS: changes armour's material to given material, even if they are the same
    public void changeMaterial(String material) {
        this.material = material;
    }

    // REQUIRES: ability name is one of: No Ability, Rapid Regen, Shockback, Nimble Rush
    // MODIFIES: this
    // EFFECTS: changes armour's defensive ability to given ability, even if they are the same
    public void changeDefensiveAbility(String defensiveAbility) {
        this.defensiveAbility = defensiveAbility;
    }

}
