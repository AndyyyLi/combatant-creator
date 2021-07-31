package persistence;

import model.*;
import model.Character;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// JsonLoaderTest test cases and test .json files based on JsonSerializationDemo's JsonReaderTest
public class JsonLoaderTest extends JsonItemTest {

    @Test
    public void testLoadNonexistentFile() {
        JsonLoader badLoader = new JsonLoader("./data/badFile.json");
        try {
            Character character = badLoader.load();
            fail("Not supposed to execute!");
        } catch (IOException e) {
            // expected!
        }
    }

    @Test
    public void testLoadDefaultCharacter() {
        JsonLoader defaultLoad = new JsonLoader("./data/testLoadDefaultCharacter.json");
        try {
            Character character = defaultLoad.load();
            assertEquals("Your combatant", character.getCharName());
            assertEquals(null, character.getCurrentWeapon());
            assertEquals(null, character.getCurrentSpell());
            assertEquals(null, character.getCurrentArmour());
            assertEquals(100, character.getTotalHealth());
            assertEquals(100, character.getTotalEnergy());
            assertEquals(0, character.getTotalWeaponDamage());
            assertEquals(0, character.getTotalSpellDamage());
            assertEquals(0, character.getTotalDefense());
            assertEquals(25, character.getTotalSpeed());
        } catch (IOException e) {
            fail("File not found!");
        }
    }

    @Test
    public void testLoadEquippedCharacter() {
        JsonLoader goodLoad = new JsonLoader("./data/testLoadEquippedCharacter.json");
        try {
            Character character = goodLoad.load();
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
        }
    }
}
