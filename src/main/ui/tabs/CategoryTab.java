package ui.tabs;

import model.*;
import model.Character;
import ui.CombatantCreatorGUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * CategoryTab abstract superclass contains the layout and formatting of all equipment tabs
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

    // GETTER METHOD
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

    // EFFECTS: creates popup dialog showing an item's information
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
    // EFFECTS: equip item onto character if possible, else show error dialog
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
        if (item instanceof Weapon) {
            if (character.getCurrentWeapon() == null
                    || !character.getCurrentWeapon().getName().equals(item.getName())) {
                removeError(item);
            } else {
                removeItem(item);
            }
        } else if (item instanceof Spell) {
            if (character.getCurrentSpell() == null
                    || !character.getCurrentSpell().getName().equals(item.getName())) {
                removeError(item);
            } else {
                removeItem(item);
            }
        } else {
            if (character.getCurrentArmour() == null
                    || !character.getCurrentArmour().getName().equals(item.getName())) {
                removeError(item);
            } else {
                removeItem(item);
            }
        }
    }

    // EFFECTS: creates error popup dialog
    public void removeError(Item item) {
        JOptionPane.showMessageDialog(null, item.getName() + " is not equipped!",
                "Remove Error", JOptionPane.WARNING_MESSAGE);
    }

    // MODIFIES: character
    // EFFECTS: removes item from character
    public void removeItem(Item item) {
        character.removeItem(item);
        refreshSummary();
        getController().playSound("remove.wav");
        JOptionPane.showMessageDialog(null, item.getName() + " has been removed!");
    }

    // MODIFIES: this
    // EFFECTS: disables all buttons if nothing is selected, else all are enabled
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (list.getSelectedIndex() == -1) {
                infoButton.setEnabled(false);
                equipButton.setEnabled(false);
                removeButton.setEnabled(false);
            } else {
                infoButton.setEnabled(true);
                equipButton.setEnabled(true);
                removeButton.setEnabled(true);
            }
        }
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
