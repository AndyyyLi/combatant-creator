package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpellTest {

    private Spell testSpell;
    private String name = "Test Spell";
    private String description = "Just a test spell.";
    private int health = 0;
    private int energy = 100;
    private int weapDmg = 0;
    private int spellDmg = 100;
    private int defense = 0;
    private int speed = 0;

    @BeforeEach
    public void setUp() {
        testSpell = new Spell(name, description, health, energy, weapDmg, spellDmg, defense, speed);
    }

    @Test
    public void testDefaultElement() {
        assertEquals("Normal", testSpell.getElement());
    }

    @Test
    public void testPickElement() {
        testSpell.pickElement("Ground");
        assertEquals("Ground", testSpell.getElement());
    }
}
