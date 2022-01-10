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
        assertEquals(testWeaponList.getWeapon4(), testWeaponList.selectItem(4));
        assertEquals(testWeaponList.getWeapon5(), testWeaponList.selectItem(5));
    }

    @Test
    public void testArrangeByHighestWeaponDamage() {
        testWeaponList.arrangeByHighestWeaponDamage();
        assertEquals(testWeaponList.getWeapon4(), testWeaponList.selectItem(1));
        assertEquals(testWeaponList.getWeapon1(), testWeaponList.selectItem(2));
        assertEquals(testWeaponList.getWeapon5(), testWeaponList.selectItem(3));
        assertEquals(testWeaponList.getWeapon3(), testWeaponList.selectItem(4));
        assertEquals(testWeaponList.getWeapon2(), testWeaponList.selectItem(5));
    }

    @Test
    public void testRevertedWeaponList() {
        testWeaponList.arrangeByHighestWeaponDamage();
        testWeaponList.arrangeWeaponsByDefault();
        assertEquals(testWeaponList.getWeapon1(), testWeaponList.selectItem(1));
        assertEquals(testWeaponList.getWeapon2(), testWeaponList.selectItem(2));
        assertEquals(testWeaponList.getWeapon3(), testWeaponList.selectItem(3));
        assertEquals(testWeaponList.getWeapon4(), testWeaponList.selectItem(4));
        assertEquals(testWeaponList.getWeapon5(), testWeaponList.selectItem(5));
    }
}
