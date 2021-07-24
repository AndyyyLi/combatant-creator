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
                0,0,0,0,0,0);
        testArmour = new Armour("Armour", "A test armour.",
                0,0,0,0,0,0);
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
        assertEquals(null, testCharacter.getCharName());
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
        int healthChange = 50;
        int defenseChange = 35;
        int speedChange = -10;

        Armour armourWithStats = new Armour("Stats Armour", "Armour with stats",
                healthChange,0, 0,0, defenseChange, speedChange);

        assertTrue(testCharacter.canEquipItem(armourWithStats));
        testCharacter.equipItem(armourWithStats);

        assertEquals(initialHealth + healthChange, testCharacter.getTotalHealth());
        assertEquals(initialDefense + defenseChange, testCharacter.getTotalDefense());
        assertEquals(initialSpeed + speedChange, testCharacter.getTotalSpeed());
    }

    @Test
    public void testCannotEquipItemBecauseNoHealth() {
        Armour badArmour = new Armour("Bad Armour", "Can't live with this!",
                -100,0,0,0,0,0);

        assertFalse(testCharacter.canEquipItem(badArmour));
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
                0,0,0,0,0,-1);

        assertFalse(testCharacter.canEquipItem(tooHeavyArmour));
    }

    @Test
    public void testSetName() {
        String testName = "Jason";

        testCharacter.setName(testName);

        assertEquals(testName, testCharacter.getCharName());
    }

    @Test
    public void testRemoveItem() {
        testCharacter.equipItem(testSpell);

        assertEquals(testCharacter.getCurrentSpell(), testSpell);

        testCharacter.removeItem(testSpell);

        assertEquals(null, testCharacter.getCurrentSpell());
    }

}
