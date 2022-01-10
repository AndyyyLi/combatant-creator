package model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SpellList class specifically covers the list of spells, and also includes exclusive method to arrange methods in
 * order of highest spell damage.
 */

public class SpellList extends Category {

    private List<Item> spellList;

    // spells in order of highest spell damage must be: 3, 4, 1, 2, 5, in order for test class to pass
    private Spell spell1 = new Spell("Spellweaver",
            "A powerful spell that can be casted quite a few times!",
            0,200,0,135,0,0,
            new ImageIcon("./imgs/spells/weaver.png"));
    private Spell spell2 = new Spell("Spell of Wonders",
            "A defensive spell which provides decent damage but additional stats.",
            65,120,0,75,35,30,
            new ImageIcon("./imgs/spells/wonders.png"));
    private Spell spell3 = new Spell("Rabadon's Spellstrike",
            "A devastating spell which sacrifices your survivability for firepower.",
            -30,75,0,250,-20,0,
            new ImageIcon("./imgs/spells/rabadons.png"));
    private Spell spell4 = new Spell("Elderspell",
            "An ancient spell dating back centuries, yet its power stayed the same after all of this time.",
            0,80,0,180,0,0,
            new ImageIcon("./imgs/spells/elder.png"));
    private Spell spell5 = new Spell("Brawler's Spell",
            "An assistive spell that somehow helps the owner boost their weapon power and agility.",
            0,50,75,50,0,65,
            new ImageIcon("./imgs/spells/brawler.png"));

    public SpellList() {
        spellList = new ArrayList<>();
        spellList.add(spell1);
        spellList.add(spell2);
        spellList.add(spell3);
        spellList.add(spell4);
        spellList.add(spell5);

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

    public Spell getSpell4() {
        return spell4;
    }

    public Spell getSpell5() {
        return spell5;
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
        spellList.add(spell4);
        spellList.add(spell5);

        super.setItemList(spellList);
    }
}
