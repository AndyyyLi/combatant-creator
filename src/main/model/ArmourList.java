package model;

import java.util.ArrayList;
import java.util.List;

/**
 * ArmourList class specifically covers the list of armours, and also includes exclusive method to arrange armours in
 * order of highest defense.
 */

public class ArmourList extends Category {

    private List<Item> armourList;

    // armours in order of highest defense must be: armour2, armour1, armour3, in order for test class to pass
    private Armour armour1 = new Armour("Knight's Battle Armour",
            "Old but always gold, enhances your spellcasting too!.",
            150,80,0,0,75,0);
    private Armour armour2 = new Armour("Armour of the Ages", "Very protective, but lacks mobility.",
            300,0,0,0,135,-15);
    private Armour armour3 = new Armour("Windrider Armour", "For those who just want speed.",
            60,0,0,0,30,65);

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

        super.setItemList(armourList);
    }
}
