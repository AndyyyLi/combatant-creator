package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpellListTest {

    private SpellList testSpellList;

    @BeforeEach
    public void setUp() {
        testSpellList = new SpellList();
    }

    @Test
    public void testConstructedSpellList() {
        assertEquals(testSpellList.getSpell1(), testSpellList.selectItem(1));
        assertEquals(testSpellList.getSpell2(), testSpellList.selectItem(2));
        assertEquals(testSpellList.getSpell3(), testSpellList.selectItem(3));
    }

    @Test
    public void testArrangeByHighestSpellDamage() {
        SpellList arrangedList = testSpellList.arrangeByHighestSpellDamage();
        assertEquals(testSpellList.getSpell3(), arrangedList.selectItem(1));
        assertEquals(testSpellList.getSpell1(), arrangedList.selectItem(2));
        assertEquals(testSpellList.getSpell2(), arrangedList.selectItem(3));
    }
}
