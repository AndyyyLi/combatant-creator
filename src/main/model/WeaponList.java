package model;

import java.util.ArrayList;
import java.util.List;

/**
 * WeaponList class specifically covers the list of weapons, and also includes exclusive method to arrange weapons in
 * order of highest weapon damage.
 */

public class WeaponList extends Category {

    private List<Item> weaponList;

    // weapons in order of highest weapon damage must be: weapon1, weapon3, weapon2
    private Weapon weapon1 = new Weapon("Weapon 1", "Highest Damage",
            0,0,85,0,0,0);
    private Weapon weapon2 = new Weapon("Weapon 2", "Lowest Damage",
            0,0,30,0,0,0);
    private Weapon weapon3 = new Weapon("Weapon 3", "Middle Damage",
            0,0,75,0,0,0);

    public WeaponList() {
        weaponList = new ArrayList<>();
        weaponList.add(weapon1);
        weaponList.add(weapon2);
        weaponList.add(weapon3);

        super.setItemList(weaponList);
    }

    // GETTERS METHODS
    public Weapon getWeapon1() {
        return weapon1;
    }

    public Weapon getWeapon2() {
        return weapon2;
    }

    public Weapon getWeapon3() {
        return weapon3;
    }

    // REQUIRES: list is in default order
    // MODIFIES: this
    // EFFECTS: arranges list by highest original weapon damage
    public void arrangeByHighestWeaponDamage() {
        weaponList.remove(weapon2);
        weaponList.add(weapon2);
    }

    // REQUIRES: list is in order of highest original weapon damage
    // MODIFIES: this
    // EFFECTS: arranges list by default
    public void arrangeWeaponsByDefault() {
        weaponList.remove(weapon3);
        weaponList.add(weapon3);
    }
}
