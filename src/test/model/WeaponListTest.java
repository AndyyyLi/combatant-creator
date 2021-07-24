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
    public void testConstructedWeaponList() {
        assertEquals(testWeaponList.getWeapon1(), testWeaponList.selectItem(1));
        assertEquals(testWeaponList.getWeapon2(), testWeaponList.selectItem(2));
        assertEquals(testWeaponList.getWeapon3(), testWeaponList.selectItem(3));
    }

    @Test
    public void testArrangeByHighestWeaponDamage() {
        testWeaponList.arrangeByHighestWeaponDamage();
        assertEquals(testWeaponList.getWeapon1(), testWeaponList.selectItem(1));
        assertEquals(testWeaponList.getWeapon3(), testWeaponList.selectItem(2));
        assertEquals(testWeaponList.getWeapon2(), testWeaponList.selectItem(3));
    }

    @Test
    public void testRevertedWeaponList() {
        testWeaponList.arrangeByHighestWeaponDamage();
        testWeaponList.arrangeWeaponsByDefault();
        assertEquals(testWeaponList.getWeapon1(), testWeaponList.selectItem(1));
        assertEquals(testWeaponList.getWeapon2(), testWeaponList.selectItem(2));
        assertEquals(testWeaponList.getWeapon3(), testWeaponList.selectItem(3));
    }
}
