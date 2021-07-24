package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    private Category testItemList;

    private List<Item> listOfSomeItems;
    private Item item1 = new Item("Item 1", "First item.",
            0,0,0,0,0,0);
    private Item item2 = new Item("Item 2", "Second item.",
            50,50,50,50,50,50);
    private Item item3 = new Item("Item 3", "Third item.",
            100,100,100,100,100,100);

    @BeforeEach
    public void setUp() {
        listOfSomeItems = new ArrayList<>();
        listOfSomeItems.add(item1);
        listOfSomeItems.add(item2);
        listOfSomeItems.add(item3);

        testItemList = new Category();
        testItemList.setItemList(listOfSomeItems);
    }

    @Test
    public void testConstructedList() {
        assertEquals(item1, testItemList.selectItem(1));
        assertEquals(item2, testItemList.selectItem(2));
        assertEquals(item3, testItemList.selectItem(3));
    }

    @Test
    public void testGetItemList() {
        List<Item> listOfItems = testItemList.getItemList();

        for (Item item : listOfSomeItems) {
            assertTrue(listOfItems.contains(item));
        }
    }
}
