package model;

import java.util.ArrayList;
import java.util.List;

/**
 * SpellList class specifically covers the list of spells, and also includes exclusive method to arrange methods in
 * order of highest spell damage.
 */

public class SpellList extends Category {

    private List<Item> spellList;

    // spells in order of highest spell damage must be: spell3, spell1, spell2
    private Spell spell1 = new Spell("Spell 1", "Middle Damage",
            0,0,0,110,0,0);
    private Spell spell2 = new Spell("Spell 2", "Lowest Damage",
            0,0,0,55,0,0);
    private Spell spell3 = new Spell("Spell 3", "Highest Damage",
            0,0,0,230,0,0);

    public SpellList() {
        spellList = new ArrayList<>();
        spellList.add(spell1);
        spellList.add(spell2);
        spellList.add(spell3);

        super.setItemList(spellList);
    }

    // GETTER METHODS
    public Spell getSpell1() {
        return spell1;
    }

    public Spell getSpell2() {
        return spell2;
    }

    public Spell getSpell3() {
        return spell3;
    }

    // REQUIRES: list is in default order
    // MODIFIES: this
    // EFFECTS: arranges list by highest original spell damage
    public void arrangeByHighestSpellDamage() {
        List<Item> arrangedList = new ArrayList<>();

        for (Item spell : spellList) {
            spell.revertItem();
            if (arrangedList.isEmpty()) {
                arrangedList.add(spell);
            } else {
                for (Item arrangedSpell : arrangedList) {
                    if (spell.getItemSpellDamage() > arrangedSpell.getItemSpellDamage()) {
                        arrangedList.add(arrangedList.indexOf(arrangedSpell), spell);
                        break;
                    }
                }
                if (!arrangedList.contains(spell)) {
                    arrangedList.add(spell);
                }
            }
        }

        spellList = arrangedList;
        super.setItemList(spellList);
    }

    // REQUIRES: list is in order of highest spell damage
    // MODIFIES: this
    // EFFECTS: arranges list by default
    public void arrangeSpellsByDefault() {
        spellList = new ArrayList<>();
        spellList.add(spell1);
        spellList.add(spell2);
        spellList.add(spell3);

        super.setItemList(spellList);
    }
}
