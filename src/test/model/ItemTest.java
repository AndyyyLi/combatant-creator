package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    private Item testItem;
    private String itemName = "Item Name";
    private String itemDescription = "This is the item's description.";
    private int health = 120;
    private int energy = 250;
    private int weapDmg = 100;
    private int spellDmg = 85;
    private int defense = 65;
    private int speed = 45;

    @BeforeEach
    public void setUp() {
        testItem = new Item(itemName, itemDescription, health, energy, weapDmg, spellDmg, defense, speed);
    }

    @Test
    public void testGetName() {
        assertEquals(itemName, testItem.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals(itemDescription, testItem.getDescription());
    }

    @Test
    public void testGetItemStats() {
        assertEquals(health, testItem.getItemHealth());
        assertEquals(energy, testItem.getItemEnergy());
        assertEquals(weapDmg, testItem.getItemWeaponDamage());
        assertEquals(spellDmg, testItem.getItemSpellDamage());
        assertEquals(defense, testItem.getItemDefense());
        assertEquals(speed, testItem.getItemSpeed());
    }

    @Test
    public void testPersonalizeItemName() {
        Character testChar = new Character();
        testChar.setName("John Smith");

        testItem.personalizeItemName(testChar);

        assertEquals(testChar.getCharName() + "'s " + itemName, testItem.getName());
    }

    @Test
    public void testExtremifyItemAllPositive() {
        testItem.extremifyItem();

        assertEquals(health * 2, testItem.getItemHealth());
        assertEquals(energy * 2, testItem.getItemEnergy());
        assertEquals(weapDmg * 2, testItem.getItemWeaponDamage());
        assertEquals(spellDmg * 2, testItem.getItemSpellDamage());
        assertEquals(defense * 2, testItem.getItemDefense());
        assertEquals(speed * 2, testItem.getItemSpeed());
    }

}
