package model;

import java.util.ArrayList;
import java.util.List;

/**
 * WeaponList class specifically covers the list of weapons, and also includes exclusive method to arrange weapons in
 * order of highest weapon damage.
 */

public class WeaponList extends Category {

    private List<Item> weaponList;

    // weapons in order of highest weapon damage must be: weapon1, weapon3, weapon2, in order for test class to pass
    private Weapon weapon1 = new Weapon("Sword of the Void",
            "Forged in the forbidden dimensions, the blade emits an intimidating yet mysterious energy.",
            -25,0,180,0,0,45);
    private Weapon weapon2 = new Weapon("Crusader's Crossbow",
            "A trusty crossbow to take down opponents from afar and a must-have for spell-lovers!",
            0,80,75,0,0,30);
    private Weapon weapon3 = new Weapon("Trinity Gauntlet",
            "Get up in their face and pack a punch that they won't forget...if they're still alive.",
            95,0,150,0,45,60);

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
        List<Item> arrangedList = new ArrayList<>();

        for (Item weapon : weaponList) {
            weapon.revertItem();
            if (arrangedList.isEmpty()) {
                arrangedList.add(weapon);
            } else {
                for (Item arrangedWeapon : arrangedList) {
                    if (weapon.getItemWeaponDamage() > arrangedWeapon.getItemWeaponDamage()) {
                        arrangedList.add(arrangedList.indexOf(arrangedWeapon), weapon);
                        break;
                    }
                }
                if (!arrangedList.contains(weapon)) {
                    arrangedList.add(weapon);
                }
            }
        }

        weaponList = arrangedList;
        super.setItemList(weaponList);
    }

    // REQUIRES: list is in order of highest original weapon damage
    // MODIFIES: this
    // EFFECTS: arranges list by default
    public void arrangeWeaponsByDefault() {
        weaponList = new ArrayList<>();
        weaponList.add(weapon1);
        weaponList.add(weapon2);
        weaponList.add(weapon3);

        super.setItemList(weaponList);
    }
}
