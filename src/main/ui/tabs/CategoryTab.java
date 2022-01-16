package ui.tabs;

import exceptions.CannotRemoveItemException;
import model.*;
import model.Character;
import ui.CombatantCreatorGUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CategoryTab abstract superclass contains the layout and formatting of all equipment tabs, largely based on
 * Tab abstract class from SmartHomeUI starter
 */
public abstract class CategoryTab extends JPanel implements ListSelectionListener {

    private final CombatantCreatorGUI controller;

    private WeaponList weaponList = new WeaponList();
    private SpellList spellList = new SpellList();
    private ArmourList armourList = new ArmourList();
    private List<Item> itemList = new ArrayList<>();

    private Character character;

    protected JList list;
    protected DefaultListModel listModel;

    protected JButton equipButton;
    protected JButton removeButton;
    protected JButton infoButton;

    public CategoryTab(CombatantCreatorGUI controller) {
        this.controller = controller;
        setLayout(new GridLayout(3, 1));

        character = controller.getCharacter();

        itemList.addAll(weaponList.getItemList());
        itemList.addAll(spellList.getItemList());
        itemList.addAll(armourList.getItemList());
    }

    // formatButtonRow method based on SmartHomeUI starter's method of the same name in the Tab class

    // MODIFIES: this
    // EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        b.setPreferredSize(new Dimension(200, 100));
        b.setFont(b.getFont().deriveFont(20f));
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
        p.add(b);

        return p;
    }

    // GUI getter method
    public CombatantCreatorGUI getController() {
        return controller;
    }

    // MODIFIES: this
    // EFFECTS: creates category name at top of window
    public void placeCategoryName(String category) {
        JLabel categoryName = new JLabel(category, JLabel.CENTER);
        categoryName.setFont(categoryName.getFont().deriveFont(64f));
        this.add(categoryName);
    }

    // initializeItems method based on ListDemoProject from the oracle.com website provided on edX

    // MODIFIES: this
    // EFFECTS: creates item list
    public void initializeItems(List<Item> items, ListSelectionListener listener) {
        listModel = new DefaultListModel();
        for (Item item : items) {
            listModel.addElement(item.getName());
        }
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(listener);
        list.setFont(list.getFont().deriveFont(30f));
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        this.add(list);
    }

    // MODIFIES: this
    // EFFECTS: creates info, equip and remove buttons
    public void placeButtons() {
        infoButton = makeInfoButton();
        equipButton = makeEquipButton();
        removeButton = makeRemoveButton();

        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        buttonRow.add(formatButtonRow(infoButton));
        buttonRow.add(formatButtonRow(equipButton));
        buttonRow.add(formatButtonRow(removeButton));

        infoButton.setEnabled(false);
        equipButton.setEnabled(false);
        removeButton.setEnabled(false);

        this.add(buttonRow);
    }

    // all usages of addActionListener in make___Button methods based on SmartHomeUI starter

    // MODIFIES: this
    // EFFECTS: helper method for making info button
    public JButton makeInfoButton() {
        JButton btn =  new JButton("Item Info");

        btn.addActionListener(e -> {
            int index = list.getSelectedIndex();
            Object item = listModel.get(index);
            String itemName = (String) item;

            for (Item i : itemList) {
                if (i.getName().equals(itemName)) {
                    displayInfo(i, 1);
                    break;
                }
            }
        });
        return btn;
    }

    // MODIFIES: this
    // EFFECTS: creates popup dialog showing item's information
    public void displayInfo(Item item, int audio) {
        if (audio == 1) {
            getController().playSound("info.wav");
        } else if (audio == 2) {
            getController().playSound("extremify.wav");
        } else if (audio == 3) {
            getController().playSound("revert.wav");
        }

        Object[] options = {"Extremify item!", "Revert item"};
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));
        panel.add(new JLabel(item.getItemIcon()));
        String stats = "<html>Name: " + item.getName()
                + "<br>Description: " + item.getDescription()
                + "<br>Health: " + item.getItemHealth()
                + "<br>Energy: " + item.getItemEnergy()
                + "<br>Weapon Damage: " + item.getItemWeaponDamage()
                + "<br>Spell Damage: " + item.getItemSpellDamage()
                + "<br>Defense: " + item.getItemDefense()
                + "<br>Speed: " + item.getItemSpeed()
                + "<br>Extremify Count: " + item.getExtremifyCount() + "</html>";
        JLabel statsLabel = new JLabel(stats);
        statsLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        panel.add(statsLabel);
//        int input = JOptionPane.showOptionDialog(null,
//                "Name: " + item.getName() + "\nDescription: " + item.getDescription()
//                        + "\nStat Changes\nHealth: " + item.getItemHealth()
//                        + "\nEnergy: " + item.getItemEnergy()
//                        + "\nWeapon Damage: " + item.getItemWeaponDamage()
//                        + "\nSpell Damage: " + item.getItemSpellDamage()
//                        + "\nDefense: " + item.getItemDefense()
//                        + "\nSpeed: " + item.getItemSpeed()
//                        + "\nExtremify Count: " + item.getExtremifyCount(), "Item Info",
//                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        int input = JOptionPane.showOptionDialog(null, panel, "Item Info",
            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (input == 0) {
            if (isEquipped(item)) {
                JOptionPane.showMessageDialog(null, "Item is equipped, cannot extremify!",
                        "Extremify Error", JOptionPane.WARNING_MESSAGE);
                displayInfo(item, 0);
            } else if (item.getExtremifyCount() == 3) {
                JOptionPane.showMessageDialog(null, "Extremify count cannot be over 3!",
                        "Extremify Error", JOptionPane.WARNING_MESSAGE);
                displayInfo(item, 0);
            } else {
                item.extremifyItem();
                displayInfo(item, 2);
            }
        } else if (input == 1) {
            if (isEquipped(item)) {
                JOptionPane.showMessageDialog(null, "Item is equipped, cannot revert!",
                        "Revert Error", JOptionPane.WARNING_MESSAGE);
                displayInfo(item, 0);
            } else if (item.getExtremifyCount() == 0) {
                JOptionPane.showMessageDialog(null, "Extremify count is zero, cannot revert!",
                        "Revert Error", JOptionPane.WARNING_MESSAGE);
                displayInfo(item, 0);
            } else {
                item.revertItem();
                displayInfo(item, 3);
            }
        }
    }

    // EFFECTS: returns true if item is equipped on character, else return false
    public boolean isEquipped(Item item) {
        if (item instanceof Weapon) {
            if (character.getCurrentWeapon() == null) return false;
            return character.getCurrentWeapon().getName().equals(item.getName());
        } else if (item instanceof Spell) {
            if (character.getCurrentSpell() == null) return false;
            return character.getCurrentSpell().getName().equals(item.getName());
        } else {
            if (character.getCurrentArmour() == null) return false;
            return character.getCurrentArmour().getName().equals(item.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: helper method for making equip button
    public JButton makeEquipButton() {
        JButton btn = new JButton("Equip Item");
        btn.addActionListener(e -> {
            int index = list.getSelectedIndex();
            Object item = listModel.get(index);
            String itemName = (String) item;
            for (Item i : itemList) {
                if (i.getName().equals(itemName)) {
                    if (i instanceof Weapon) {
                        itemCompare(character.getCurrentWeapon(), i);
                    } else if (i instanceof Spell) {
                        itemCompare(character.getCurrentSpell(), i);
                    } else {
                        itemCompare(character.getCurrentArmour(), i);
                    }
                    break;
                }
            }
        });
        return btn;
    }

    // EFFECTS: equips item if character does not have an item of that category equipped,
    //          else show dialog depending on whether another item is equipped or same item is equipped
    public void itemCompare(Item current, Item replacement) {
        if (current == null) {
            equipItem(replacement);
        } else if (current.getName().equals(replacement.getName())) {
            JOptionPane.showMessageDialog(null, current.getName() + " is already equipped!",
                    "Equip Error", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, current.getName() + " must be removed first!",
                    "Equip Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    // MODIFIES: character
    // EFFECTS: equip item onto character if possible, else show equip error dialog
    public void equipItem(Item item) {
        if (character.canEquipItem(item)) {
            boolean toEquip = customizeItem(item);
            if (toEquip) {
                character.equipItem(item);
                refreshSummary();
                getController().playSound("equip.wav");
                JOptionPane.showMessageDialog(null, item.getName() + " has been equipped!");
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    item.getName() + "'s stats are not compatible with character's current stats.",
                    "Equip Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    // MODIFIES: item
    // EFFECTS: appropriately modifies item depending on item type
    public boolean customizeItem(Item item) {
        if (item instanceof Weapon) {
            // pick hit effect and intensity
            return customizeWeapon((Weapon) item);
        } else if (item instanceof Spell) {
            // pick element
            return customizeSpell((Spell) item);
        } else {
            // pick material and defensive ability
            return customizeArmour((Armour) item);
        }
    }

    // MODIFIES: weapon
    // EFFECTS: prompts user to select hit effect and intensity
    private boolean customizeWeapon(Weapon weapon) {
        JRadioButton none = new JRadioButton("No Effect");
        JRadioButton stun = new JRadioButton("Stun");
        JRadioButton arBr = new JRadioButton("Armour Break");
        JRadioButton pierce = new JRadioButton("Pierce");
        JRadioButton multi = new JRadioButton("Multihit");

        ButtonGroup hitFx = new ButtonGroup();
        hitFx.add(none);
        hitFx.add(stun);
        hitFx.add(arBr);
        hitFx.add(pierce);
        hitFx.add(multi);

        JPanel btnPanel = new JPanel();
        btnPanel.add(none);
        btnPanel.add(stun);
        btnPanel.add(arBr);
        btnPanel.add(pierce);
        btnPanel.add(multi);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2,1));
        panel1.add(new JLabel("Select a hit effect:"));
        panel1.add(btnPanel);

//        JOptionPane.showMessageDialog(null, panel1, "Hit Effect",
//                JOptionPane.INFORMATION_MESSAGE);

        int input1 = JOptionPane.showOptionDialog(null, panel1, "Hit Effect",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Select"},
                JOptionPane.OK_OPTION);

        String effect;
        if (input1 != JOptionPane.CLOSED_OPTION) {
            if (none.isSelected()) {
                weapon.applyHitEffect("No Effect");
                weapon.setEffectIntensity(0);
                return true;
            } else if (stun.isSelected()) {
                weapon.applyHitEffect("Stun");
                effect = "the stun duration in seconds";
            } else if (arBr.isSelected()) {
                weapon.applyHitEffect("Armour Break");
                effect = "the amount of armour broken (1 = 10%, 5 = 50%)";
            } else if (pierce.isSelected()) {
                weapon.applyHitEffect("Pierce");
                effect = "the number of enemies it can pierce through";
            } else if (multi.isSelected()) {
                weapon.applyHitEffect("Multihit");
                effect = "the number of additional enemies that can be hit at once";
            } else {
                JOptionPane.showMessageDialog(null, "Nothing Selected!", "Equip Error",
                        JOptionPane.WARNING_MESSAGE);
                return customizeWeapon(weapon);
            }
        } else {
            return false;
        }


        JSlider slider = createSlider();

        String info = "<html>Select " + weapon.getHitEffect() + "'s intensity level.<br>Each level indicates " + effect +
                ",<br>but the chances of it occurring decrease as the level increases.</html>";
        JPanel txtPanel = new JPanel();
        txtPanel.add(new JLabel(info));

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2,1));
        panel2.add(txtPanel);
        panel2.add(slider);

        int input2 = JOptionPane.showOptionDialog(null, panel2, "Effect Intensity",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (input2 == JOptionPane.OK_OPTION) {
            weapon.setEffectIntensity(slider.getValue());
            return true;
        } else {
            return false;
        }
    }

    // private helper for customizeWeapon's effect intensity slider
    private JSlider createSlider() {
        JSlider slider = new JSlider(1,5);
        slider.setMajorTickSpacing(1);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setValue(3);
        return slider;
    }

    // MODIFIES: spell
    // EFFECTS: prompts user to select an element
    private boolean customizeSpell(Spell spell) {
        JRadioButton normal = new JRadioButton("Normal");
        JRadioButton fire = new JRadioButton("Fire");
        JRadioButton water = new JRadioButton("Water");
        JRadioButton ground = new JRadioButton("Ground");

        ButtonGroup elements = new ButtonGroup();
        elements.add(normal);
        elements.add(fire);
        elements.add(water);
        elements.add(ground);

        JPanel btnPanel = new JPanel();
        btnPanel.add(normal);
        btnPanel.add(fire);
        btnPanel.add(water);
        btnPanel.add(ground);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2,1));
        panel1.add(new JLabel("Select an element:"));
        panel1.add(btnPanel);

//        JOptionPane.showMessageDialog(null, panel1, "Spell Element",
//                JOptionPane.INFORMATION_MESSAGE);

        int input = JOptionPane.showOptionDialog(null, panel1, "Spell Element",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Select"},
                JOptionPane.OK_OPTION);

        if (input != JOptionPane.CLOSED_OPTION) {
            if (normal.isSelected()) {
                spell.pickElement("Normal");
            } else if (fire.isSelected()) {
                spell.pickElement("Fire");
            } else if (water.isSelected()) {
                spell.pickElement("Water");
            } else if (ground.isSelected()) {
                spell.pickElement("Ground");
            } else {
                JOptionPane.showMessageDialog(null, "Nothing selected, defaulting to Normal!",
                        "Default Selected", JOptionPane.INFORMATION_MESSAGE);
                spell.pickElement("Normal");
            }
        } else {
            return false;
        }

        return true;
    }

    // MODIFIES: armour
    // EFFECTS: prompts user to select armour material and defensive ability
    private boolean customizeArmour(Armour armour) {
        JRadioButton steel = new JRadioButton("Super Steel");
        JRadioButton diamond = new JRadioButton("Ice Diamond");
        JRadioButton obsidian = new JRadioButton("Magma Obsidian");

        ButtonGroup materials = new ButtonGroup();
        materials.add(steel);
        materials.add(diamond);
        materials.add(obsidian);

        JPanel btnPanel = new JPanel();
        btnPanel.add(steel);
        btnPanel.add(diamond);
        btnPanel.add(obsidian);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2,1));
        panel1.add(new JLabel("Select armour material:"));
        panel1.add(btnPanel);

//        JOptionPane.showMessageDialog(null, panel1, "Armour Material",
//                JOptionPane.INFORMATION_MESSAGE);

        int input1 = JOptionPane.showOptionDialog(null, panel1, "Armour Material",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Select"},
                JOptionPane.OK_OPTION);

        if (input1 != JOptionPane.CLOSED_OPTION) {
            if (steel.isSelected()) {
                armour.changeMaterial("Super Steel");
            } else if (diamond.isSelected()) {
                armour.changeMaterial("Ice Diamond");
            } else if (obsidian.isSelected()) {
                armour.changeMaterial("Magma Obsidian");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Nothing selected, defaulting to Super Steel!", "Default Selected",
                        JOptionPane.INFORMATION_MESSAGE);
                armour.changeMaterial("Super Steel");
            }
        } else {
            return false;
        }

        JRadioButton none = new JRadioButton("None");
        JRadioButton regen = new JRadioButton("Rapid Regen - Briefly and quickly heal");
        JRadioButton shock = new JRadioButton("Shockback - Paralyze and disarm attackers");
        JRadioButton rush = new JRadioButton("Nimble Rush - Gain a burst of speed and dodge attacks");

        ButtonGroup abilities = new ButtonGroup();
        abilities.add(none);
        abilities.add(regen);
        abilities.add(shock);
        abilities.add(rush);

        JPanel btnPanel2 = new JPanel();
        btnPanel2.setLayout(new GridLayout(4,1));
        btnPanel2.add(none);
        btnPanel2.add(regen);
        btnPanel2.add(shock);
        btnPanel2.add(rush);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2,1));
        panel2.add(new JLabel("Select your armour's defensive ability, " +
                "which occasionally triggers upon being hit:"));
        panel2.add(btnPanel2);

//        JOptionPane.showMessageDialog(null, panel2, "Defensive Ability",
//                JOptionPane.INFORMATION_MESSAGE);

        int input2 = JOptionPane.showOptionDialog(null, panel2, "Defensive Ability",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Select"},
                JOptionPane.OK_OPTION);

        if (input2 != JOptionPane.CLOSED_OPTION) {
            if (none.isSelected()) {
                armour.changeDefensiveAbility("None");
            } else if (regen.isSelected()) {
                armour.changeDefensiveAbility("Rapid Regen");
            } else if (shock.isSelected()) {
                armour.changeDefensiveAbility("Shockback");
            } else if (rush.isSelected()) {
                armour.changeDefensiveAbility("Nimble Rush");
            } else {
                JOptionPane.showMessageDialog(null, "Nothing selected, defaulting to None!",
                        "Default Selected", JOptionPane.INFORMATION_MESSAGE);
                armour.changeDefensiveAbility("None");
            }
        } else {
            return false;
        }

        return true;
    }

    // MODIFIES: this
    // EFFECTS: helper method for making remove button
    public JButton makeRemoveButton() {
        JButton btn = new JButton("Remove Item");
        btn.addActionListener(e -> {
            int index = list.getSelectedIndex();
            Object item = listModel.get(index);
            String itemName = (String) item;

            for (Item i : itemList) {
                if (i.getName().equals(itemName)) {
                    tryToRemove(i);
                    break;
                }
            }
        });
        return btn;
    }

    // EFFECTS: compares character's equipped item with given item, removes only if they are the same,
    //          else show error popup message
    public void tryToRemove(Item item) {
        if (character.canRemove(item)) {
            try {
                character.removeItem(item);
                refreshSummary();
                getController().playSound("remove.wav");
                JOptionPane.showMessageDialog(null, item.getName() + " has been removed!");
            } catch (CannotRemoveItemException e) {
                JOptionPane.showMessageDialog(null, item.getName() + " is not equipped!",
                        "Remove Error", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cannot remove; stats will turn negative!",
                    "Remove Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: keeps the buttons clickable while user is selecting between the items
    @Override
    public void valueChanged(ListSelectionEvent e) {
        infoButton.setEnabled(true);
        equipButton.setEnabled(true);
        removeButton.setEnabled(true);
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
    }

}
