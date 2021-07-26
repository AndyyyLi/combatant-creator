package model;

import java.util.ArrayList;
import java.util.List;

/**
 * SpellList class specifically covers the list of spells, and also includes exclusive method to arrange methods in
 * order of highest spell damage.
 */

public class SpellList extends Category {

    private List<Item> spellList;

    // spells in order of highest spell damage must be: spell3, spell1, spell2, in order for test class to pass
    private Spell spell1 = new Spell("Spellweaver",
            "A powerful spell that can be casted quite a few times!",
            0,200,0,135,0,0);
    private Spell spell2 = new Spell("Spell of Wonders",
            "A defensive spell which provides decent damage but additional stats.",
            65,120,0,75,35,30);
    private Spell spell3 = new Spell("Rabadon's Spellstrike",
            "A devastating spell which sacrifices your survivability for firepower.",
            -30,50,0,250,-20,0);

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
