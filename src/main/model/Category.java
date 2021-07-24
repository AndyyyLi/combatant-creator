package model;

import java.util.List;

/**
 * Category superclass covers the basic functionality of every type of items list, and declares every list to be
 * exactly three items long.
 */

public class Category {

    private List<Item> itemList;

    public void setItemList(List<Item> list) {
        itemList = list;
    }

    // GETTER METHOD
    public List<Item> getItemList() {
        return itemList;
    }

    // REQUIRES: position must be 1, 2, or 3
    // EFFECTS: returns item at given position in given category
    public Item selectItem(int position) {
        return null;
    }

}
