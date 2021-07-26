package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {

    private Character testCharacter;
    private Weapon testWeapon;
    private Spell testSpell;
    private Armour testArmour;

    @BeforeEach
    public void setUp() {
        testCharacter = new Character();
        testWeapon = new Weapon("Weapon", "A test weapon.",
                0,0,0,0,0,0);
        testSpell = new Spell("Spell", "A test spell.",
                50,100,25,90,30,-5);
        testArmour = new Armour("Armour", "A test armour.",
                200,-15,30,25,75,10);
    }

    @Test
    public void testCheckBaseStats() {
        assertEquals(100, testCharacter.getTotalHealth());
        assertEquals(100, testCharacter.getTotalEnergy());
        assertEquals(0, testCharacter.getTotalWeaponDamage());
        assertEquals(0, testCharacter.getTotalSpellDamage());
        assertEquals(0, testCharacter.getTotalDefense());
        assertEquals(25, testCharacter.getTotalSpeed());
    }

    @Test
    public void testConstructedName() {
        assertEquals("Your combatant", testCharacter.getCharName());
    }

    @Test
    public void testCheckNoEquippedItems() {
        assertEquals(null, testCharacter.getCurrentWeapon());
        assertEquals(null, testCharacter.getCurrentSpell());
        assertEquals(null, testCharacter.getCurrentArmour());
    }

    @Test
    public void testCanEquipAndCheckOneItem() {
        assertTrue(testCharacter.canEquipItem(testWeapon));
        testCharacter.equipItem(testWeapon);

        assertEquals(testWeapon, testCharacter.getCurrentWeapon());
    }

    @Test
    public void testCanEquipAndCheckFullItems() {
        assertTrue(testCharacter.canEquipItem(testWeapon));
        assertTrue(testCharacter.canEquipItem(testSpell));
        assertTrue(testCharacter.canEquipItem(testArmour));
        testCharacter.equipItem(testWeapon);
        testCharacter.equipItem(testSpell);
        testCharacter.equipItem(testArmour);

        assertEquals(testWeapon, testCharacter.getCurrentWeapon());
        assertEquals(testSpell, testCharacter.getCurrentSpell());
        assertEquals(testArmour, testCharacter.getCurrentArmour());
    }

    @Test
    public void testCanEquipTwoItemsOfSameCategory() {
        Weapon testExtraWeapon = new Weapon("Extra", "I'm extra!",
                0,0,0,0,0,0);

        assertTrue(testCharacter.canEquipItem(testWeapon));
        assertTrue(testCharacter.canEquipItem(testExtraWeapon));
        testCharacter.equipItem(testWeapon);
        testCharacter.equipItem(testExtraWeapon);

        assertEquals(testExtraWeapon, testCharacter.getCurrentWeapon());
    }

    @Test
    public void testCanEquipItemWithStatChangesThenCheckStats() {
        int initialHealth = testCharacter.getTotalHealth();
        int initialDefense = testCharacter.getTotalDefense();
        int initialSpeed = testCharacter.getTotalSpeed();

        assertTrue(testCharacter.canEquipItem(testArmour));
        testCharacter.equipItem(testArmour);

        assertEquals(initialHealth + testArmour.getItemHealth(), testCharacter.getTotalHealth());
        assertEquals(initialDefense + testArmour.getItemDefense(), testCharacter.getTotalDefense());
        assertEquals(initialSpeed + testArmour.getItemSpeed(), testCharacter.getTotalSpeed());
    }

    @Test
    public void testCannotEquipItemBecauseNoHealth() {
        Armour badArmour = new Armour("Bad Armour", "Can't live with this!",
                -100,0,0,0,0,0);

        assertFalse(testCharacter.canEquipItem(badArmour));
        testCharacter.equipItem(badArmour);
        assertEquals(null, testCharacter.getCurrentArmour());
    }

    @Test
    public void testCannotEquipItemBecauseNoEnergy() {
        Spell badSpell = new Spell("Bad Spell", "Can't cast this!",
                0,-100,0,0,0,0);

        assertFalse(testCharacter.canEquipItem(badSpell));
    }

    @Test
    public void testCannotEquipItemBecauseNegWeapDmg() {
        Weapon sadWeapon = new Weapon("Sad Weapon", "I don't work!",
                0,0,-1,0,0,0);

        assertFalse(testCharacter.canEquipItem(sadWeapon));
    }

    @Test
    public void testCannotEquipItemBecauseNegSpellDmg() {
        Spell sadSpell = new Spell("Sad Spell", "I don't work!",
                0,0,0,-1,0,0);

        assertFalse(testCharacter.canEquipItem(sadSpell));
    }

    @Test
    public void testCannotEquipItemBecauseNegDefense() {
        Armour sadArmour = new Armour("Sad Armour", "Can't wear this!",
                0,0,0,0,-1,0);

        assertFalse(testCharacter.canEquipItem(sadArmour));
    }

    @Test
    public void testCannotEquipItemBecauseNegSpeed() {
        Armour tooHeavyArmour = new Armour("Too Heavy Armour", "Can't move with this!",
                0,0,0,0,0,-25);

        assertFalse(testCharacter.canEquipItem(tooHeavyArmour));
    }

    @Test
    public void testSetName() {
        String testName = "Jason";

        testCharacter.setName(testName);
        assertEquals(testName, testCharacter.getCharName());
    }

    @Test
    public void testEquipThenRemoveItemWithStats() {
        testCharacter.equipItem(testWeapon);
        testCharacter.equipItem(testSpell);
        testCharacter.equipItem(testArmour);

        assertEquals(testWeapon, testCharacter.getCurrentWeapon());
        assertEquals(testSpell, testCharacter.getCurrentSpell());
        assertEquals(testArmour, testCharacter.getCurrentArmour());

        testCharacter.removeItem(testWeapon);
        testCharacter.removeItem(testSpell);
        testCharacter.removeItem(testArmour);

        assertEquals(null, testCharacter.getCurrentWeapon());
        assertEquals(null, testCharacter.getCurrentSpell());
        assertEquals(null, testCharacter.getCurrentArmour());

        assertEquals(100, testCharacter.getTotalHealth());
        assertEquals(100, testCharacter.getTotalEnergy());
        assertEquals(0, testCharacter.getTotalWeaponDamage());
        assertEquals(0, testCharacter.getTotalSpellDamage());
        assertEquals(0, testCharacter.getTotalDefense());
        assertEquals(25, testCharacter.getTotalSpeed());
    }

}
