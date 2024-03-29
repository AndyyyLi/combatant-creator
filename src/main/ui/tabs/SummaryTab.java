package ui.tabs;

import exceptions.CannotRemoveItemException;
import exceptions.InvalidNameException;
import jdk.nashorn.internal.scripts.JO;
import model.Armour;
import model.Character;
import model.Spell;
import model.Weapon;
import persistence.JsonSaver;
import ui.CombatantCreatorGUI;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

/**
 * SummaryTab class creates the tab for the character's summary, and also contains the buttons to rename character,
 * save character, and finish game (which closes the application)
 */
public class SummaryTab extends JPanel {

    private final CombatantCreatorGUI controller;

    private Character character;
    private JPanel equipment;
    private JPanel stats;
    private JPanel options;

    private JsonSaver jsonSaver;

    public SummaryTab(CombatantCreatorGUI controller) {
        this.controller = controller;
        this.character = controller.getCharacter();

        jsonSaver = new JsonSaver(CombatantCreatorGUI.JSON_FILE);

        setLayout(new GridLayout(4, 1));

        placeName();
        placeEquipment();
        placeStats();
        placeOptions();
    }

    // GUI getter method
    public CombatantCreatorGUI getController() {
        return controller;
    }

    // MODIFIES: this
    // EFFECTS: places character name at top of screen
    public void placeName() {
        JLabel characterName = new JLabel(character.getCharName(), JLabel.CENTER);
        characterName.setFont(characterName.getFont().deriveFont(64f));
        this.add(characterName);
    }

    // MODIFIES: this
    // EFFECTS: places character's equipment's names on screen
    public void placeEquipment() {
        equipment = new JPanel();
        equipment.setLayout(new GridLayout(3,1));
        JLabel weapon = new JLabel(weaponName());
        JLabel spell = new JLabel(spellName());
        JLabel armour = new JLabel(armourName());

        weapon.setFont(weapon.getFont().deriveFont(25f));
        spell.setFont(spell.getFont().deriveFont(25f));
        armour.setFont(armour.getFont().deriveFont(25f));

        weapon.setHorizontalAlignment(JLabel.CENTER);
        spell.setHorizontalAlignment(JLabel.CENTER);
        armour.setHorizontalAlignment(JLabel.CENTER);

        equipment.add(weapon);
        equipment.add(spell);
        equipment.add(armour);

        this.add(equipment);
    }

    // EFFECTS: if character has no weapon equipped, return "No Weapon Equipped", else return equipped weapon name
    public String weaponName() {
        String name;
        Weapon weapon = character.getCurrentWeapon();
        if (weapon == null) {
            name = "No Weapon Equipped";
        } else {
            if (weapon.getHitEffect().equals("No Effect")) {
                name = "Weapon: " + weapon.getName();
            } else {
                name = "Weapon: " + weapon.getName() + " (" + weapon.getHitEffect() + " " +
                        weapon.getEffectIntensity() + ")";
            }
        }
        return name;
    }

    // EFFECTS: if character has no spell equipped, return "No Spell Equipped", else return equipped spell name
    public String spellName() {
        String name;
        Spell spell = character.getCurrentSpell();
        if (spell == null) {
            name = "No Spell Equipped";
        } else {
            if (spell.getElement().equals("Normal")) {
                name = "Spell: " + spell.getName();
            } else {
                name = "Spell: " + spell.getName() + " (" + spell.getElement() + ")";
            }
        }
        return name;
    }

    // EFFECTS: if character has no armour equipped, return "No Armour Equipped", else return equipped armour name
    public String armourName() {
        String name;
        Armour armour = character.getCurrentArmour();
        if (armour == null) {
            name = "No Armour Equipped";
        } else {
            if (armour.getDefensiveAbility().equals("None")) {
                name = "Armour: " + armour.getName() + " (" + armour.getMaterial() + ") ";
            } else {
                name = "Armour: " + armour.getName() + " (" + armour.getMaterial() + ", "
                        + armour.getDefensiveAbility() + ")";
            }
        }
        return name;
    }

    // MODIFIES: this
    // EFFECTS: places all of characters stats on screen
    public void placeStats() {
        JPanel totalStats = new JPanel();
        totalStats.setLayout(new GridLayout(2,1));
        JLabel title = new JLabel("Total Stats", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(24f));
        totalStats.add(title);

        stats = new JPanel();
        stats.setLayout(new GridLayout(3,2));

        makeStats(stats);
        stats.setBorder(BorderFactory.createEmptyBorder(0,50,15,0));
        totalStats.add(stats);

        this.add(totalStats);
    }

    // EFFECTS: helper method that creates all the stats labels
    public void makeStats(JPanel stats) {
        JLabel health = new JLabel("Total Health: " + character.getTotalHealth());
        JLabel energy = new JLabel("Total Energy: " + character.getTotalEnergy());
        JLabel weapDmg = new JLabel("Total Weapon Damage: " + character.getTotalWeaponDamage());
        JLabel spellDmg = new JLabel("Total Spell Damage: " + character.getTotalSpellDamage());
        JLabel defense = new JLabel("Total Defense: " + character.getTotalDefense());
        JLabel speed = new JLabel("Total Speed: " + character.getTotalSpeed());

        setFontSize(health);
        setFontSize(energy);
        setFontSize(weapDmg);
        setFontSize(spellDmg);
        setFontSize(defense);
        setFontSize(speed);

        stats.add(health);
        stats.add(energy);
        stats.add(weapDmg);
        stats.add(spellDmg);
        stats.add(defense);
        stats.add(speed);
    }

    // MODIFIES: stat JLabels
    // EFFECTS: sets JLabel font size
    public void setFontSize(JLabel stat) {
        stat.setFont(stat.getFont().deriveFont(18f));
    }

    // MODIFIES: this
    // EFFECTS: places option buttons at bottom of screen
    public void placeOptions() {
        options = new JPanel();
        options.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));

        JButton name = makeNameButton();
        JButton save = makeSaveButton();
        JButton reset = makeResetButton();
        JButton finish = makeFinishButton();

        options.add(name);
        options.add(save);
        options.add(reset);
        options.add(finish);

        this.add(options);
    }

    // MODIFIES: this
    // EFFECTS: makes name button that prompts user to change their character's name
    private JButton makeNameButton() {
        JButton btn = new JButton("Change Name");
        btn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Current name: " + character.getCharName()
                    + "\nEnter new name:");

            if (name != null) {
                while (name != null) {
                    try {
                        character.setName(name);
                        refreshTab();
                        getController().playSound("name.wav");
                        JOptionPane.showMessageDialog(null, "Your character is now named "
                                + character.getCharName());
                        break;
                    } catch (InvalidNameException invalidNameException) {
                        name = JOptionPane.showInputDialog("Name cannot be empty! Please try again."
                                + "\nEnter new name:");
                    }
                }
            }
        });
        btn.setPreferredSize(new Dimension(200,80));
        btn.setFont(btn.getFont().deriveFont(20f));
        return btn;
    }

    // MODIFIES: this
    // EFFECTS: makes save button that saves character's name and equipment, shows save error dialog if unable to save
    private JButton makeSaveButton() {
        JButton btn = new JButton("Save Character");
        btn.addActionListener(e -> {
            try {
                jsonSaver.open();
                jsonSaver.save(character);
                jsonSaver.close();
                getController().playSound("save.wav");
                JOptionPane.showMessageDialog(null,
                        character.getCharName() + " has been saved to file!");
            } catch (FileNotFoundException exception) {
                JOptionPane.showMessageDialog(null, "Cannot save to file: "
                        + CombatantCreatorGUI.JSON_FILE, "Save Error", JOptionPane.WARNING_MESSAGE);
            }
        });
        btn.setPreferredSize(new Dimension(200,80));
        btn.setFont(btn.getFont().deriveFont(20f));
        return btn;
    }

    // MODIFIES: this
    // EFFECTS: makes reset button which confirms that user wants to reset their character,
    //          and if so, unequips all currently equipped items
    private JButton makeResetButton() {
        JButton btn = new JButton("Reset Character");
        btn.addActionListener(e -> {
            int input = JOptionPane.showOptionDialog(null,
                    "Are you sure you want to unequip all items from " + character.getCharName() + "?",
                    "Confirm Reset", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null,null,null);
            if (input == 0) {
                Weapon weapon = character.getCurrentWeapon();
                Spell spell = character.getCurrentSpell();
                Armour armour = character.getCurrentArmour();
                try {
                    if (character.getCurrentWeapon() != null) {
                        character.removeItem(weapon);
                    }
                    if (character.getCurrentSpell() != null) {
                        character.removeItem(spell);
                    }
                    if (character.getCurrentArmour() != null) {
                        character.removeItem(armour);
                    }
                    refreshSummary();
                    getController().playSound("reset.wav");
                    JOptionPane.showMessageDialog(null,
                            character.getCharName() + " has been reset!", "Reset Complete",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (CannotRemoveItemException cannotRemoveItemException) {
                    JOptionPane.showMessageDialog(null, "Sorry, cannot reset character!",
                            "Reset Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btn.setPreferredSize(new Dimension(200,80));
        btn.setFont(btn.getFont().deriveFont(20f));
        return btn;
    }

    // MODIFIES: summary tab
    // EFFECTS: reloads summary tab to update character's equipment and total stats
    private void refreshSummary() {
        JTabbedPane tabBar = getController().getTabBar();
        tabBar.remove(CombatantCreatorGUI.SUMMARY_TAB_INDEX);
        tabBar.add(new SummaryTab(getController()));
        tabBar.setTitleAt(CombatantCreatorGUI.SUMMARY_TAB_INDEX, "Character Summary");
        tabBar.revalidate();
        tabBar.repaint();
        tabBar.setSelectedIndex(CombatantCreatorGUI.SUMMARY_TAB_INDEX);
    }

    // MODIFIES: this
    // EFFECTS: makes finish button which confirms that user wants to finish then if so, displays finish popup dialog
    //          and exits application
    public JButton makeFinishButton() {
        JButton btn = new JButton("Finish Game");
        btn.addActionListener(e -> {
            int input = JOptionPane.showOptionDialog(null, "Are you sure you're done?",
                    "Finish?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, null, null);
            if (input == 0) {
                getController().playSound("finish.wav");
                UIManager.put("OptionPane.okButtonText", "Finish");
                JOptionPane.showMessageDialog(null,
                        character.getCharName() + " is ready for battle!", "Character Complete!",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });
        btn.setPreferredSize(new Dimension(200,80));
        btn.setFont(btn.getFont().deriveFont(20f));
        return btn;
    }

    // MODIFIES: this
    // EFFECTS: refreshes tab to reload updated character
    public void refreshTab() {
        JTabbedPane tab = controller.getTabBar();
        tab.remove(CombatantCreatorGUI.SUMMARY_TAB_INDEX);
        tab.add(new SummaryTab(controller));
        tab.setTitleAt(CombatantCreatorGUI.SUMMARY_TAB_INDEX, "Character Summary");
        tab.revalidate();
        tab.repaint();
        tab.setSelectedIndex(CombatantCreatorGUI.SUMMARY_TAB_INDEX);
    }
}
