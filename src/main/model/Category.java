package model;

import java.util.List;

/**
 * Category superclass covers the basic functionality of lists of every type of item
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

    // REQUIRES: position must be between 1 and the size of item list (inclusive)
    // EFFECTS: returns item at given position in given category
    public Item selectItem(int position) {
        return null;
    }

}
