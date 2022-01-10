package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponTest {

    private Weapon testWeapon;
    private String name = "Test Weapon";
    private String description = "Just a test weapon.";
    private int health = 0;
    private int energy = 0;
    private int weapDmg = 80;
    private int spellDmg = 0;
    private int defense = 0;
    private int speed = 50;

    @BeforeEach
    public void setUp() {
        testWeapon = new Weapon(name, description, health, energy, weapDmg, spellDmg, defense, speed, null);
    }

    @Test
    public void testDefaultHitEffect() {
        assertEquals("No Effect", testWeapon.getHitEffect());
    }

    @Test
    public void testDefaultEffectIntensity() {
        assertEquals(0, testWeapon.getEffectIntensity());
    }

    @Test
    public void testApplyLowIntensityHitEffect() {
        testWeapon.applyHitEffect("Multihit");
        testWeapon.setEffectIntensity(1);

        assertEquals("Multihit", testWeapon.getHitEffect());
        assertEquals(1, testWeapon.getEffectIntensity());
    }

    @Test
    public void testApplyHighIntensityHitEffect() {
        testWeapon.applyHitEffect("Stun");
        testWeapon.setEffectIntensity(3);

        assertEquals("Stun", testWeapon.getHitEffect());
        assertEquals(3, testWeapon.getEffectIntensity());
    }

}
