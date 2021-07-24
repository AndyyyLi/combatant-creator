package model;

import java.util.ArrayList;
import java.util.List;

public class WeaponList extends Category {

    public WeaponList() {
        List<Item> weaponList = new ArrayList<>();

        super.setItemList(weaponList);
    }
}
