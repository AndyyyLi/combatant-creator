package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponListTest {

    private WeaponList testWeaponList;

    @BeforeEach
    public void setUp() {
        testWeaponList = new WeaponList();
    }

    @Test
    public void testConstructedArmourList() {
        assertEquals(testWeaponList.getWeapon1(), testWeaponList.selectItem(1));
        assertEquals(testWeaponList.getWeapon2(), testWeaponList.selectItem(2));
        assertEquals(testWeaponList.getWeapon3(), testWeaponList.selectItem(3));

    }

    @Test
    public void testArrangeByHighestWeaponDamage() {
        WeaponList arrangedList = testWeaponList.arrangeByHighestWeaponDamage();
        assertEquals(testWeaponList.getWeapon1(), arrangedList.selectItem(1));
        assertEquals(testWeaponList.getWeapon3(), arrangedList.selectItem(2));
        assertEquals(testWeaponList.getWeapon2(), arrangedList.selectItem(3));
    }
}
