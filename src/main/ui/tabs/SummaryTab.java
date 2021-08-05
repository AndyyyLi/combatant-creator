package ui.tabs;

import model.Character;
import persistence.JsonLoader;
import persistence.JsonSaver;
import ui.CombatantCreatorGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public void placeName() {
        JLabel characterName = new JLabel(character.getCharName(), JLabel.CENTER);
        characterName.setFont(characterName.getFont().deriveFont(64f));
        this.add(characterName);
    }

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

    public String weaponName() {
        String name;
        if (character.getCurrentWeapon() == null) {
            name = "No Weapon Equipped";
        } else {
            name = "Current Weapon: " + character.getCurrentWeapon().getName();
        }
        return name;
    }

    public String spellName() {
        String name;
        if (character.getCurrentSpell() == null) {
            name = "No Spell Equipped";
        } else {
            name = "Current Spell: " + character.getCurrentSpell().getName();
        }
        return name;
    }

    public String armourName() {
        String name;
        if (character.getCurrentArmour() == null) {
            name = "No Armour Equipped";
        } else {
            name = "Current Armour: " + character.getCurrentArmour().getName();
        }
        return name;
    }

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

    public JPanel makeStats(JPanel stats) {
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

        return stats;
    }

    public void setFontSize(JLabel stat) {
        stat.setFont(stat.getFont().deriveFont(18f));
    }

    public void placeOptions() {
        options = new JPanel();
        options.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));

        JButton name = makeOption("Change Name");
        name.addActionListener(new NameChangeListener());

        JButton save = makeOption("Save Character");
        save.addActionListener(new SaveListener());

        JButton finish = makeOption("Finish Game");
        finish.addActionListener(new FinishGameListener());

        options.add(name);
        options.add(save);
        options.add(finish);

        this.add(options);
    }

    private JButton makeOption(String option) {
        JButton optionBtn = new JButton(option);
        optionBtn.setActionCommand(option);
        optionBtn.setPreferredSize(new Dimension(200,80));
        optionBtn.setFont(optionBtn.getFont().deriveFont(20f));
        return optionBtn;
    }

    public void refreshTab() {
        JTabbedPane tabBar = controller.getTabBar();
        tabBar.remove(CombatantCreatorGUI.SUMMARY_TAB_INDEX);
        tabBar.add(new SummaryTab(controller));
        tabBar.setTitleAt(CombatantCreatorGUI.SUMMARY_TAB_INDEX, "Character Summary");
        tabBar.revalidate();
        tabBar.repaint();
        tabBar.setSelectedIndex(CombatantCreatorGUI.SUMMARY_TAB_INDEX);
    }

    public class NameChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = JOptionPane.showInputDialog("Current name: " + character.getCharName()
                    + "\nEnter new name:");
            if (name != null) {
                character.setName(name);
                refreshTab();
                JOptionPane.showMessageDialog(null, "Your character is now named "
                        + character.getCharName());
            }
        }
    }

    public class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonSaver.open();
                jsonSaver.save(character);
                jsonSaver.close();
                JOptionPane.showMessageDialog(null,
                        character.getCharName() + " has been saved to file!");
            } catch (FileNotFoundException exception) {
                JOptionPane.showMessageDialog(null, "Cannot save to file: "
                        + CombatantCreatorGUI.JSON_FILE, "Save Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public class FinishGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int input = JOptionPane.showConfirmDialog(null, "Are you sure you're done?");
            if (input == 0) {
                UIManager.put("OptionPane.okButtonText", "Finish");
                JOptionPane.showMessageDialog(null,
                        character.getCharName() + " is ready for battle!");
                System.exit(0);
            }
        }
    }
}
