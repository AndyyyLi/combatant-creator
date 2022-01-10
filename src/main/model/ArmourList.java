package model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ArmourList class specifically covers the list of armours, and also includes exclusive method to arrange armours in
 * order of highest defense.
 */

public class ArmourList extends Category {

    private List<Item> armourList;

    // armours in order of highest defense must be: 2, 1, 5, 4, 3, in order for test class to pass
    private Armour armour1 = new Armour("Knight's Battle Armour",
            "Old but always gold, enhances your attacks too!",
            150,0,25,25,100,0,
            new ImageIcon("./imgs/armour/knight.png"));
    private Armour armour2 = new Armour("Armour of the Ages", "Very protective, but lacks mobility.",
            300,0,0,0,130,-15,
            new ImageIcon("./imgs/armour/ages.png"));
    private Armour armour3 = new Armour("Windrider Armour", "For those who just want speed.",
            60,0,0,0,30,65,
            new ImageIcon("./imgs/armour/windrider.png"));
    private Armour armour4 = new Armour("Hide of the Nomad",
            "Comfortable, highly valuable, and good for ambushes.",
            180, 0, 35, 0, 70, 45,
            new ImageIcon("./imgs/armour/nomad.png"));
    private Armour armour5 = new Armour("Fairy-Sparkling Plate", "Blessed with the power of magic!",
            120,75,0,60,90,15,
            new ImageIcon("./imgs/armour/fairy.png"));
    public ArmourList() {
        armourList = new ArrayList<>();
        armourList.add(armour1);
        armourList.add(armour2);
        armourList.add(armour3);
        armourList.add(armour4);
        armourList.add(armour5);

        super.setItemList(armourList);
    }

    // GETTER METHODS
    public Armour getArmour1() {
        return armour1;
    }

    public Armour getArmour2() {
        return armour2;
    }

    public Armour getArmour3() {
        return armour3;
    }

    public Armour getArmour4() {
        return armour4;
    }

    public Armour getArmour5() {
        return armour5;
    }

    // REQUIRES: list is in default order
    // MODIFIES: this
    // EFFECTS: arranges list by highest original defense
    public void arrangeByHighestDefense() {
        List<Item> arrangedList = new ArrayList<>();

        for (Item armour : armourList) {
            armour.revertItem();
            if (arrangedList.isEmpty()) {
                arrangedList.add(armour);
            } else {
                for (Item arrangedArmor : arrangedList) {
                    if (armour.getItemDefense() > arrangedArmor.getItemDefense()) {
                        arrangedList.add(arrangedList.indexOf(arrangedArmor), armour);
                        break;
                    }
                }
                if (!arrangedList.contains(armour)) {
                    arrangedList.add(armour);
                }
            }
        }

        armourList = arrangedList;
        super.setItemList(armourList);
    }

    // REQUIRES: list is in order of highest original defense
    // MODIFIES: this
    // EFFECTS: arranges list by default
    public void arrangeArmoursByDefault() {
        armourList = new ArrayList<>();
        armourList.add(armour1);
        armourList.add(armour2);
        armourList.add(armour3);
        armourList.add(armour4);
        armourList.add(armour5);

        super.setItemList(armourList);
    }
}
