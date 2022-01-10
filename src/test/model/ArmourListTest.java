package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArmourListTest {

    private ArmourList testArmourList;

    @BeforeEach
    public void setUp() {
        testArmourList = new ArmourList();
    }

    @Test
    public void testConstructedArmourList() {
        assertEquals(testArmourList.getArmour1(), testArmourList.selectItem(1));
        assertEquals(testArmourList.getArmour2(), testArmourList.selectItem(2));
        assertEquals(testArmourList.getArmour3(), testArmourList.selectItem(3));
        assertEquals(testArmourList.getArmour4(), testArmourList.selectItem(4));
        assertEquals(testArmourList.getArmour5(), testArmourList.selectItem(5));
    }

    @Test
    public void testArrangeByHighestDefense() {
        testArmourList.arrangeByHighestDefense();
        assertEquals(testArmourList.getArmour2(), testArmourList.selectItem(1));
        assertEquals(testArmourList.getArmour1(), testArmourList.selectItem(2));
        assertEquals(testArmourList.getArmour5(), testArmourList.selectItem(3));
        assertEquals(testArmourList.getArmour4(), testArmourList.selectItem(4));
        assertEquals(testArmourList.getArmour3(), testArmourList.selectItem(5));
    }

    @Test
    public void testRevertedArmourList() {
        testArmourList.arrangeByHighestDefense();
        testArmourList.arrangeArmoursByDefault();
        assertEquals(testArmourList.getArmour1(), testArmourList.selectItem(1));
        assertEquals(testArmourList.getArmour2(), testArmourList.selectItem(2));
        assertEquals(testArmourList.getArmour3(), testArmourList.selectItem(3));
        assertEquals(testArmourList.getArmour4(), testArmourList.selectItem(4));
        assertEquals(testArmourList.getArmour5(), testArmourList.selectItem(5));
    }
}
