package model;

import java.util.ArrayList;
import java.util.List;

public class SpellList extends Category {

    public SpellList() {
        List<Item> spellList = new ArrayList<>();

        super.setItemList(spellList);
    }

}
