package model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * WeaponList class specifically covers the list of weapons, and also includes exclusive method to arrange weapons in
 * order of highest weapon damage.
 */

public class WeaponList extends Category {

    private List<Item> weaponList;

    // weapons in order of highest weapon damage must be: 4, 1, 5, 3, 2, in order for test class to pass
    private Weapon weapon1 = new Weapon("Sword of the Void",
            "Forged in the forbidden dimensions, the blade emits an intimidating yet mysterious energy.",
            -25,0,180,0,0,45,
            new ImageIcon("./imgs/weapons/sword.png"));
    private Weapon weapon2 = new Weapon("Crusader's Crossbow",
            "A trusty crossbow to take down opponents from afar and a must-have for spell-lovers!",
            0,80,75,0,0,30,
            new ImageIcon("./imgs/weapons/crossbow.png"));
    private Weapon weapon3 = new Weapon("Trinity Gauntlets",
            "Get up in their face and pack a punch that they won't forget...if they're still alive.",
            95,0,120,0,45,60,
            new ImageIcon("./imgs/weapons/gauntlet.png"));
    private Weapon weapon4 = new Weapon("Supercharged Bazooka", "Full firepower ahead.",
            0,0,250,0,0,-15,
            new ImageIcon("./imgs/weapons/rocket.png"));
    private Weapon weapon5 = new Weapon("Massacre Mace",
            "A flailing menace, forged to fight and defend within a medium range around the wielder.",
            60,0,150,0,80,0,
            new ImageIcon("./imgs/weapons/mace.png"));

    public WeaponList() {
        weaponList = new ArrayList<>();
        weaponList.add(weapon1);
        weaponList.add(weapon2);
        weaponList.add(weapon3);
        weaponList.add(weapon4);
        weaponList.add(weapon5);

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

    public Weapon getWeapon4() {
        return weapon4;
    }

    public Weapon getWeapon5() {
        return weapon5;
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
        weaponList.add(weapon4);
        weaponList.add(weapon5);

        super.setItemList(weaponList);
    }
}
