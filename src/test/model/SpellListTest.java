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
        assertEquals(testSpellList.getSpell4(), testSpellList.selectItem(4));
        assertEquals(testSpellList.getSpell5(), testSpellList.selectItem(5));
    }

    @Test
    public void testArrangeByHighestSpellDamage() {
        testSpellList.arrangeByHighestSpellDamage();
        assertEquals(testSpellList.getSpell3(), testSpellList.selectItem(1));
        assertEquals(testSpellList.getSpell4(), testSpellList.selectItem(2));
        assertEquals(testSpellList.getSpell1(), testSpellList.selectItem(3));
        assertEquals(testSpellList.getSpell2(), testSpellList.selectItem(4));
        assertEquals(testSpellList.getSpell5(), testSpellList.selectItem(5));
    }

    @Test
    public void testRevertedSpellList() {
        testSpellList.arrangeByHighestSpellDamage();
        testSpellList.arrangeSpellsByDefault();
        assertEquals(testSpellList.getSpell1(), testSpellList.selectItem(1));
        assertEquals(testSpellList.getSpell2(), testSpellList.selectItem(2));
        assertEquals(testSpellList.getSpell3(), testSpellList.selectItem(3));
        assertEquals(testSpellList.getSpell4(), testSpellList.selectItem(4));
        assertEquals(testSpellList.getSpell5(), testSpellList.selectItem(5));
    }
}
