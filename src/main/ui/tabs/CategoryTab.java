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

        JPanel buttonRow = formatButtonRow(infoButton);
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
                    displayInfo(i);
                    break;
                }
            }
        });
        return btn;
    }

    // MODIFIES: this
    // EFFECTS: creates popup dialog showing item's information
    public void displayInfo(Item item) {
        getController().playSound("info.wav");
        JOptionPane.showMessageDialog(null,
                "Name: " + item.getName() + "\nDescription: " + item.getDescription()
                        + "\nStat Changes\nHealth: " + item.getItemHealth()
                        + "\nEnergy: " + item.getItemEnergy()
                        + "\nWeapon Damage: " + item.getItemWeaponDamage()
                        + "\nSpell Damage: " + item.getItemSpellDamage()
                        + "\nDefense: " + item.getItemDefense()
                        + "\nSpeed: " + item.getItemSpeed(), "Item Info", JOptionPane.PLAIN_MESSAGE);
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
            character.equipItem(item);
            refreshSummary();
            getController().playSound("equip.wav");
            JOptionPane.showMessageDialog(null, item.getName() + " has been equipped!");
        } else {
            JOptionPane.showMessageDialog(null,
                    item.getName() + "'s stats are not compatible with character's current stats.",
                    "Equip Error", JOptionPane.WARNING_MESSAGE);
        }
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
        try {
            character.removeItem(item);
            refreshSummary();
            getController().playSound("remove.wav");
            JOptionPane.showMessageDialog(null, item.getName() + " has been removed!");
        } catch (CannotRemoveItemException e) {
            JOptionPane.showMessageDialog(null, item.getName() + " is not equipped!",
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
    public void refreshSummary() {
        JTabbedPane tabBar = getController().getTabBar();
        tabBar.remove(CombatantCreatorGUI.SUMMARY_TAB_INDEX);
        tabBar.add(new SummaryTab(getController()));
        tabBar.setTitleAt(CombatantCreatorGUI.SUMMARY_TAB_INDEX, "Character Summary");
        tabBar.revalidate();
        tabBar.repaint();
    }

}
