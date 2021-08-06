package ui.tabs;

import model.Character;
import persistence.JsonSaver;
import ui.CombatantCreatorGUI;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

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

    // GETTER METHOD
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

    // EFFECTS: if character has no weapon equipped, display "No Weapon Equipped", else show equipped weapon name
    public String weaponName() {
        String name;
        if (character.getCurrentWeapon() == null) {
            name = "No Weapon Equipped";
        } else {
            name = "Current Weapon: " + character.getCurrentWeapon().getName();
        }
        return name;
    }

    // EFFECTS: if character has no spell equipped, display "No Spell Equipped", else show equipped spell name
    public String spellName() {
        String name;
        if (character.getCurrentSpell() == null) {
            name = "No Spell Equipped";
        } else {
            name = "Current Spell: " + character.getCurrentSpell().getName();
        }
        return name;
    }

    // EFFECTS: if character has no armour equipped, display "No Armour Equipped", else show equipped armour name
    public String armourName() {
        String name;
        if (character.getCurrentArmour() == null) {
            name = "No Armour Equipped";
        } else {
            name = "Current Armour: " + character.getCurrentArmour().getName();
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

    // EFFECTS: creates all the stats labels
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
        JButton finish = makeFinishButton();

        options.add(name);
        options.add(save);
        options.add(finish);

        this.add(options);
    }

    // EFFECTS: makes name button
    private JButton makeNameButton() {
        JButton btn = new JButton("Change Name");
        btn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Current name: " + character.getCharName()
                    + "\nEnter new name:");
            if (name != null) {
                character.setName(name);
                refreshTab();
                getController().playSound("name.wav");
                JOptionPane.showMessageDialog(null, "Your character is now named "
                        + character.getCharName());
            }
        });
        btn.setPreferredSize(new Dimension(200,80));
        btn.setFont(btn.getFont().deriveFont(20f));
        return btn;
    }

    // EFFECTS: makes save button
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

    // EFFECTS: makes finish button
    public JButton makeFinishButton() {
        JButton btn = new JButton("Finish Game");
        btn.addActionListener(e -> {
            int input = JOptionPane.showConfirmDialog(null, "Are you sure you're done?");
            if (input == 0) {
                getController().playSound("finish.wav");
                UIManager.put("OptionPane.okButtonText", "Finish");
                JOptionPane.showMessageDialog(null,
                        character.getCharName() + " is ready for battle!");
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
