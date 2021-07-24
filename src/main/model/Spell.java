package model;

/**
 * Spell is a type of Item which mainly provides stat changes to energy and spell damage, and also include
 * exclusive options to choose their spell's element type
 */

public class Spell extends Item {

    // element can be Normal (default), Fire, Water, or Ground
    private String element;

    public Spell(String name, String description, int health, int energy, int weaponDamage, int spellDamage,
                 int defense, int speed) {
        super(name, description, health, energy, weaponDamage, spellDamage, defense, speed);
        element = "Normal";
    }

    // GETTER METHOD
    public String getElement() {
        return element;
    }

    // REQUIRES: element name is one of: Normal, Fire, Water, Ground
    // MODIFIES: this
    // EFFECTS: sets element to given element, even if they are the same
    public void pickElement(String element) {
        this.element = element;
    }

}
