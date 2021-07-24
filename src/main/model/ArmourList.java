package model;

import java.util.ArrayList;
import java.util.List;

/**
 * ArmourList class specifically covers the list of armours, and also includes exclusive method to arrange armours in
 * order of highest defense.
 */

public class ArmourList extends Category {

    private List<Item> armourList;

    // armours in order of highest defense must be: armour2, armour3, armour1
    private Armour armour1 = new Armour("Armour 1", "Lowest Defense",
            0,0,0,0,35,0);
    private Armour armour2 = new Armour("Armour 2", "Highest Defense",
            0,0,0,0,105,0);
    private Armour armour3 = new Armour("Armour 3", "Middle Defense",
            0,0,0,0,60,0);

    public ArmourList() {
        armourList = new ArrayList<>();
        armourList.add(armour1);
        armourList.add(armour2);
        armourList.add(armour3);

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

    // REQUIRES: list is in default order
    // MODIFIES: this
    // EFFECTS: arranges list by highest original defense
    public void arrangeByHighestDefense() {
        armourList.remove(armour1);
        armourList.add(armour1);
    }

    // REQUIRES: list is in order of highest original defense
    // MODIFIES: this
    // EFFECTS: arranges list by default
    public void arrangeArmoursByDefault() {
        armourList.remove(armour2);
        armourList.remove(armour3);
        armourList.add(armour2);
        armourList.add(armour3);
    }
}
