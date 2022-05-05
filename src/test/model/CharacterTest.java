package model;

import exceptions.CannotRemoveItemException;
import exceptions.InvalidNameException;
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
               0,0,0,0,0,0, null);
       testSpell = new Spell("Spell", "A test spell.",
               50,100,25,90,30,-5, null);
       testArmour = new Armour("Armour", "A test armour.",
               200,-15,30,25,75,10, null);
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
               0,0,0,0,0,0, null);

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
               -100,0,0,0,0,0, null);

       assertFalse(testCharacter.canEquipItem(badArmour));
       testCharacter.equipItem(badArmour);
       assertEquals(null, testCharacter.getCurrentArmour());
   }

   @Test
   public void testCannotEquipItemBecauseNoEnergy() {
       Spell badSpell = new Spell("Bad Spell", "Can't cast this!",
               0,-100,0,0,0,0, null);

       assertFalse(testCharacter.canEquipItem(badSpell));
   }

   @Test
   public void testCannotEquipItemBecauseNegWeapDmg() {
       Weapon sadWeapon = new Weapon("Sad Weapon", "I don't work!",
               0,0,-1,0,0,0, null);

       assertFalse(testCharacter.canEquipItem(sadWeapon));
   }

   @Test
   public void testCannotEquipItemBecauseNegSpellDmg() {
       Spell sadSpell = new Spell("Sad Spell", "I don't work!",
               0,0,0,-1,0,0, null);

       assertFalse(testCharacter.canEquipItem(sadSpell));
   }

   @Test
   public void testCannotEquipItemBecauseNegDefense() {
       Armour sadArmour = new Armour("Sad Armour", "Can't wear this!",
               0,0,0,0,-1,0, null);

       assertFalse(testCharacter.canEquipItem(sadArmour));
   }

   @Test
   public void testCannotEquipItemBecauseNegSpeed() {
       Armour tooHeavyArmour = new Armour("Too Heavy Armour", "Can't move with this!",
               0,0,0,0,0,-25, null);

       assertFalse(testCharacter.canEquipItem(tooHeavyArmour));
   }

   @Test
   public void testSetNameNoExceptionThrown() {
       String testName = "Jason";

       try {
           testCharacter.setName(testName);
           assertEquals(testName, testCharacter.getCharName());
       } catch (InvalidNameException e) {
           fail("Unexpected invalid name exception!");
       }
   }

   @Test
   public void testSetEmptyNameExceptionThrown() {
       try {
           testCharacter.setName("");
           fail("Method should not have executed!");
       } catch (InvalidNameException e) {
           // expected
       }

       assertEquals("Your combatant", testCharacter.getCharName());
   }

   @Test
   public void testSetNullNameExceptionThrown() {
       try {
           testCharacter.setName(null);
           fail("Method should not have executed!");
       } catch (InvalidNameException e) {
           // expected
       }

       assertEquals("Your combatant", testCharacter.getCharName());
   }

   @Test
   public void testEquipThenRemoveItemsNoExceptionThrown() {
       testCharacter.equipItem(testWeapon);
       testCharacter.equipItem(testSpell);
       testCharacter.equipItem(testArmour);

       assertEquals(testWeapon, testCharacter.getCurrentWeapon());
       assertEquals(testSpell, testCharacter.getCurrentSpell());
       assertEquals(testArmour, testCharacter.getCurrentArmour());

       try {
           testCharacter.removeItem(testWeapon);
           testCharacter.removeItem(testSpell);
           testCharacter.removeItem(testArmour);
       } catch (CannotRemoveItemException e) {
           fail("Unexpected caught exception!");
       }

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

   @Test
   public void testRemoveNonEquippedWeaponExceptionThrown() {
       try {
           testCharacter.removeItem(testWeapon);
           fail("Method should not execute!");
       } catch (CannotRemoveItemException e) {
           // expected
       }
       assertEquals(null, testCharacter.getCurrentWeapon());
   }

   @Test
   public void testRemoveNonEquippedSpellExceptionThrown() {
       try {
           testCharacter.removeItem(testSpell);
           fail("Method should not execute!");
       } catch (CannotRemoveItemException e) {
           // expected
       }
       assertEquals(null, testCharacter.getCurrentSpell());
   }

   @Test
   public void testRemoveNonEquippedArmourExceptionThrown() {
       try {
           testCharacter.removeItem(testArmour);
           fail("Method should not execute!");
       } catch (CannotRemoveItemException e) {
           // expected
       }
       assertEquals(null, testCharacter.getCurrentArmour());
   }

   @Test
   public void testRemoveAnotherWeaponExceptionThrown() {
       Weapon anotherWeapon = new Weapon("Unexpected Weapon", "I'm not supposed to be here!",
               0,0,0,0,0,0, null);
       try {
           testCharacter.equipItem(testWeapon);
           testCharacter.removeItem(anotherWeapon);
           fail("Method should not execute!");
       } catch (CannotRemoveItemException e) {
           // expected
       }
       assertEquals(testWeapon, testCharacter.getCurrentWeapon());
   }

   @Test
   public void testRemoveAnotherSpellExceptionThrown() {
       Spell anotherSpell = new Spell("Unexpected Spell", "I'm not supposed to be here!",
               0,0,0,0,0,0, null);
       try {
           testCharacter.equipItem(testSpell);
           testCharacter.removeItem(anotherSpell);
           fail("Method should not execute!");
       } catch (CannotRemoveItemException e) {
           // expected
       }
       assertEquals(testSpell, testCharacter.getCurrentSpell());
   }

   @Test
   public void testRemoveAnotherArmourExceptionThrown() {
       Armour anotherArmour = new Armour("Unexpected Armour", "I'm not supposed to be here!",
               0,0,0,0,0,0, null);
       try {
           testCharacter.equipItem(testArmour);
           testCharacter.removeItem(anotherArmour);
           fail("Method should not execute!");
       } catch (CannotRemoveItemException e) {
           // expected
       }
       assertEquals(testArmour, testCharacter.getCurrentArmour());
   }

}
