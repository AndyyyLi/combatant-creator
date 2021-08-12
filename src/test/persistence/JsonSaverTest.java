package persistence;

import exceptions.InvalidNameException;
import model.*;
import model.Character;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// JsonSaverTest test cases and test .json files based on JsonSerializationDemo's JsonWriterTest
public class JsonSaverTest extends JsonItemTest {

    @Test
    public void testSaveNonexistentFile() {
        try {
            Character badCharacter = new Character();
            JsonSaver saver = new JsonSaver("./data/illegal\0File.json");
            saver.open();
            fail("Should not have executed!");
        } catch (IOException e) {
            // expected!
        }
    }

    @Test
    public void testSaveDefaultCharacter() {
        try {
            Character defaultCharacter = new Character();
            JsonSaver defaultSaver = new JsonSaver("./data/testSaveDefaultCharacter.json");
            defaultSaver.open();
            defaultSaver.save(defaultCharacter);
            defaultSaver.close();

            JsonLoader defaultLoad = new JsonLoader("./data/testSaveDefaultCharacter.json");
            defaultCharacter = defaultLoad.load();
            assertEquals("Your combatant", defaultCharacter.getCharName());
            assertEquals(null, defaultCharacter.getCurrentWeapon());
            assertEquals(null, defaultCharacter.getCurrentSpell());
            assertEquals(null, defaultCharacter.getCurrentArmour());
            assertEquals(100, defaultCharacter.getTotalHealth());
            assertEquals(100, defaultCharacter.getTotalEnergy());
            assertEquals(0, defaultCharacter.getTotalWeaponDamage());
            assertEquals(0, defaultCharacter.getTotalSpellDamage());
            assertEquals(0, defaultCharacter.getTotalDefense());
            assertEquals(25, defaultCharacter.getTotalSpeed());
        } catch (IOException e) {
            fail("File not found!");
        } catch (InvalidNameException e) {
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testSaveEquippedCharacter() {
        try {
            WeaponList weapons = new WeaponList();
            SpellList spells = new SpellList();
            ArmourList armours = new ArmourList();
            Character character = new Character();

            character.setName("Pantheon");

            weapons.getWeapon1().extremifyItem();
            weapons.getWeapon1().applyHitEffect("Multihit");
            weapons.getWeapon1().setEffectIntensity(3);
            character.equipItem(weapons.getWeapon1());

            spells.getSpell2().extremifyItem();
            spells.getSpell2().extremifyItem();
            spells.getSpell2().pickElement("Fire");
            character.equipItem(spells.getSpell2());

            armours.getArmour3().extremifyItem();
            armours.getArmour3().changeMaterial("Magma Obsidian");
            armours.getArmour3().changeDefensiveAbility("Rapid Regen");
            character.equipItem(armours.getArmour3());

            JsonSaver saver = new JsonSaver("./data/testSaveEquippedCharacter.json");
            saver.open();
            saver.save(character);
            saver.close();

            JsonLoader goodLoad = new JsonLoader("./data/testSaveEquippedCharacter.json");

            character = goodLoad.load();
            WeaponList weaponList = goodLoad.getWeaponList();
            SpellList spellList = goodLoad.getSpellList();
            ArmourList armourList = goodLoad.getArmourList();
            Weapon actualWeapon = weaponList.getWeapon1();
            Spell actualSpell = spellList.getSpell2();
            Armour actualArmour = armourList.getArmour3();

            assertEquals("Pantheon", character.getCharName());
            checkItem(actualWeapon, character.getCurrentWeapon());
            assertEquals(actualWeapon.getHitEffect(), character.getCurrentWeapon().getHitEffect());
            assertEquals(actualWeapon.getEffectIntensity(), character.getCurrentWeapon().getEffectIntensity());
            checkItem(actualSpell, character.getCurrentSpell());
            assertEquals(actualSpell.getElement(), character.getCurrentSpell().getElement());
            checkItem(actualArmour, character.getCurrentArmour());
            assertEquals(actualArmour.getMaterial(), character.getCurrentArmour().getMaterial());
            assertEquals(actualArmour.getDefensiveAbility(), character.getCurrentArmour().getDefensiveAbility());

            assertEquals(430, character.getTotalHealth());
            assertEquals(580, character.getTotalEnergy());
            assertEquals(360, character.getTotalWeaponDamage());
            assertEquals(300, character.getTotalSpellDamage());
            assertEquals(200, character.getTotalDefense());
            assertEquals(365, character.getTotalSpeed());
        } catch (IOException e) {
            fail("File not found!");
        } catch (InvalidNameException ine) {
            fail("Unexpected invalid name exception");
        }
    }
}
