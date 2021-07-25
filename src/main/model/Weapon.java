package model;

/**
 * Weapon is a type of Item which mainly provides stat changes to weapon damage and speed, and also include
 * exclusive options to add a hit effect and choose the effect's intensity, which varies per effect
 */

public class Weapon extends Item {

    // hit effect can be No Effect (default), Stun, Armour Break, Pierce, Multihit
    private String hitEffect;

    // effect intensity can be a number between 0 and 3 (inclusive), with 3 being very intense and 0 being not
    // intense, and is always 0 for No Effect
    private int effectIntensity;

    public Weapon(String name, String description, int health, int energy, int weaponDamage, int spellDamage,
                  int defense, int speed) {
        super(name, description, health, energy, weaponDamage, spellDamage, defense, speed);
        hitEffect = "No Effect";
        effectIntensity = 0;
    }

    // GETTER METHODS
    public String getHitEffect() {
        return hitEffect;
    }

    public int getEffectIntensity() {
        return effectIntensity;
    }


    // REQUIRES: hit effect name is one of: No Effect, Stun, Armour Break, Pierce, Multihit
    // MODIFIES: this
    // EFFECTS: changes hit effect to given effect, even if they are the same
    public void applyHitEffect(String hitEffect) {
        this.hitEffect = hitEffect;
    }

    // REQUIRES: effect intensity is in range [1,5] except for No Effect, which must have 0 intensity
    // MODIFIES: this
    // EFFECTS: sets effect intensity to given number, even if they are the same
    public void setEffectIntensity(int effectIntensity) {
        this.effectIntensity = effectIntensity;
    }

}
