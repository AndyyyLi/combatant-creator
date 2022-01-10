package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArmourTest {

    private Armour testArmour;
    private String name = "Test Armour";
    private String description = "Just a test armour.";
    private int health = 100;
    private int energy = 0;
    private int weapDmg = 0;
    private int spellDmg = 0;
    private int defense = 50;
    private int speed = 20;

    @BeforeEach
    public void setUp() {
        testArmour = new Armour(name, description, health, energy, weapDmg, spellDmg, defense, speed, null);
    }

    @Test
    public void testDefaultMaterial() {
        assertEquals("Super Steel", testArmour.getMaterial());
    }

    @Test
    public void testDefaultDefensiveAbility() {
        assertEquals("None", testArmour.getDefensiveAbility());
    }

    @Test
    public void testChangeMaterial() {
        testArmour.changeMaterial("Ice Diamond");
        assertEquals("Ice Diamond", testArmour.getMaterial());
    }

    @Test
    public void testChangeDefensiveAbility() {
        testArmour.changeDefensiveAbility("Rapid Regen");
        assertEquals("Rapid Regen", testArmour.getDefensiveAbility());
    }

}
