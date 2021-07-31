package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.*;
import model.Character;
import org.json.*;

/**
 * JsonLoader class loads character and exact equipment (including special features and extremifications) from file
 * All methods above and including parseCharacter() are based on JsonSerializationDemo's JsonReader class
 */
public class JsonLoader {
    private String source;
    private WeaponList weaponList = new WeaponList();
    private SpellList spellList = new SpellList();
    private ArmourList armourList = new ArmourList();

    // EFFECTS: constructs reader to read from source file
    public JsonLoader(String source) {
        this.source = source;
    }

    // GETTER METHODS
    public WeaponList getWeaponList() {
        return weaponList;
    }

    public SpellList getSpellList() {
        return spellList;
    }

    public ArmourList getArmourList() {
        return armourList;
    }



    // EFFECTS: loads character from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Character load() throws IOException {
        String jsonData = loadFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCharacter(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String loadFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses JSON object to character and returns character
    public Character parseCharacter(JSONObject jsonObject) {
        String charName = jsonObject.getString("name");

        Character character = new Character();
        character.setName(charName);

        if (!jsonObject.getString("weapon").equals("None")) {
            addWeapon(character, jsonObject);
        }

        if (!jsonObject.getString("spell").equals("None")) {
            addSpell(character, jsonObject);
        }

        if (!jsonObject.getString("armour").equals("None")) {
            addArmour(character, jsonObject);
        }

        return character;
    }

    // MODIFIES: character
    // EFFECTS: parses JSON objects to weapon its extremify count and equips weapon onto character
    public void addWeapon(Character character, JSONObject jsonObject) {
        String weaponName = jsonObject.getString("weapon");
        int weaponAdjust = jsonObject.getInt("weaponAdjust");
        String weaponEffect = jsonObject.getString("weaponEffect");
        int weaponIntensity = jsonObject.getInt("weaponIntensity");

        for (Item weapon : weaponList.getItemList()) {
            if (weapon.getName().equals(weaponName)) {
                for (int i = 0; i < weaponAdjust; i++) {
                    weapon.extremifyItem();
                }
                Weapon realWeapon = (Weapon) weapon;
                realWeapon.applyHitEffect(weaponEffect);
                realWeapon.setEffectIntensity(weaponIntensity);
                character.setItem(realWeapon);
            }
        }
    }

    // MODIFIES: character
    // EFFECTS: parses JSON objects to spell and its extremify count and equips spell onto character
    public void addSpell(Character character, JSONObject jsonObject) {
        String spellName = jsonObject.getString("spell");
        int spellAdjust = jsonObject.getInt("spellAdjust");
        String spellElement = jsonObject.getString("spellElement");

        for (Item spell : spellList.getItemList()) {
            if (spell.getName().equals(spellName)) {
                for (int i = 0; i < spellAdjust; i++) {
                    spell.extremifyItem();
                }
                Spell realSpell = (Spell) spell;
                realSpell.pickElement(spellElement);
                character.setItem(realSpell);
            }
        }
    }

    // MODIFIES: character
    // EFFECTS: parses JSON objects to armour and its extremify count and equips armour onto character
    public void addArmour(Character character, JSONObject jsonObject) {
        String armourName = jsonObject.getString("armour");
        int armourAdjust = jsonObject.getInt("armourAdjust");
        String armourMaterial = jsonObject.getString("armourMaterial");
        String armourAbility = jsonObject.getString("armourAbility");

        for (Item armour : armourList.getItemList()) {
            if (armour.getName().equals(armourName)) {
                for (int i = 0; i < armourAdjust; i++) {
                    armour.extremifyItem();
                }
                Armour realArmour = (Armour) armour;
                realArmour.changeMaterial(armourMaterial);
                realArmour.changeDefensiveAbility(armourAbility);
                character.setItem(realArmour);
            }
        }
    }
}
