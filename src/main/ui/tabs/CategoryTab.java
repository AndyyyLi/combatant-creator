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

public abstract class CategoryTab extends JPanel implements ListSelectionListener {

    private final CombatantCreatorGUI controller;

    private WeaponList weaponList = new WeaponList();
    private SpellList spellList = new SpellList();
    private ArmourList armourList = new ArmourList();

    private List<Item> itemList = new ArrayList<>();

    protected JList list;
    protected DefaultListModel listModel;

    protected JButton equipButton;
    protected JButton removeButton;
    protected JButton infoButton;

    public CategoryTab(CombatantCreatorGUI controller) {
        this.controller = controller;
        setLayout(new GridLayout(3, 1));

        itemList.addAll(weaponList.getItemList());
        itemList.addAll(spellList.getItemList());
        itemList.addAll(armourList.getItemList());
    }

    // EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        b.setPreferredSize(new Dimension(200,100));
        b.setFont(b.getFont().deriveFont(20f));
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
        p.add(b);

        return p;
    }

    public CombatantCreatorGUI getController() {
        return controller;
    }

    // EFFECTS: creates category name at top of window
    public void placeCategoryName(String category) {
        JLabel categoryName = new JLabel(category, JLabel.CENTER);
        categoryName.setFont(categoryName.getFont().deriveFont(64f));
        this.add(categoryName);
    }

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

    // EFFECTS: creates info, equip and remove buttons
    public void placeButtons() {
        infoButton = new JButton("Item Info");
        infoButton.setActionCommand("Item Info");
        infoButton.addActionListener(new InfoListener());

        equipButton = new JButton("Equip Item");
        equipButton.setActionCommand("Equip Item");
        equipButton.addActionListener(new EquipListener());

        removeButton = new JButton("Remove Item");
        removeButton.setActionCommand("Remove Item");
        removeButton.addActionListener(new RemoveListener());

        JPanel buttonRow = formatButtonRow(infoButton);
        buttonRow.add(formatButtonRow(equipButton));
        buttonRow.add(formatButtonRow(removeButton));

        infoButton.setEnabled(false);
        equipButton.setEnabled(false);
        removeButton.setEnabled(false);

        this.add(buttonRow);
    }

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

    public void refreshSummary() {
        JTabbedPane tabBar = getController().getTabBar();
        tabBar.remove(CombatantCreatorGUI.SUMMARY_TAB_INDEX);
        tabBar.add(new SummaryTab(getController()));
        tabBar.setTitleAt(CombatantCreatorGUI.SUMMARY_TAB_INDEX, "Character Summary");
        tabBar.revalidate();
        tabBar.repaint();
    }

    public class InfoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            Object item = listModel.get(index);
            String itemName = (String) item;

            for (Item i : itemList) {
                if (i.getName().equals(itemName)) {
                    displayInfo(i);
                    break;
                }
            }
        }

        public void displayInfo(Item item) {
            JOptionPane.showMessageDialog(null,
                    "Name: " + item.getName() + "\nDescription: " + item.getDescription()
                            + "\nStat Changes\nHealth: " + item.getItemHealth()
                            + "\nEnergy: " + item.getItemEnergy()
                            + "\nWeapon Damage: " + item.getItemWeaponDamage()
                            + "\nSpell Damage: " + item.getItemSpellDamage()
                            + "\nDefense: " + item.getItemDefense()
                            + "\nSpeed: " + item.getItemSpeed(), "Item Info", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public class EquipListener implements ActionListener {
        Character character = getController().getCharacter();

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            Object item = listModel.get(index);
            String itemName = (String) item;

            for (Item i : itemList) {
                if (i.getName().equals(itemName)) {
                    tryToEquip(i);
                    break;
                }
            }
        }

        public void tryToEquip(Item item) {
            if (item instanceof Weapon) {
                itemCompare(character.getCurrentWeapon(), item);
            } else if (item instanceof Spell) {
                itemCompare(character.getCurrentSpell(), item);
            } else {
                itemCompare(character.getCurrentArmour(), item);
            }
        }

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

        public void equipItem(Item item) {
            if (character.canEquipItem(item)) {
                character.equipItem(item);
                refreshSummary();
                JOptionPane.showMessageDialog(null, item.getName() + " has been equipped!");
            } else {
                JOptionPane.showMessageDialog(null,
                        item.getName() + "'s stats are not compatible with character's current stats.",
                        "Equip Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public class RemoveListener implements ActionListener {
        Character character = getController().getCharacter();

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            Object item = listModel.get(index);
            String itemName = (String) item;

            for (Item i : itemList) {
                if (i.getName().equals(itemName)) {
                    tryToRemove(i);
                    break;
                }
            }
        }

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

        public void removeError(Item item) {
            JOptionPane.showMessageDialog(null, item.getName() + " is not equipped!",
                    "Remove Error", JOptionPane.WARNING_MESSAGE);
        }

        public void removeItem(Item item) {
            character.removeItem(item);
            refreshSummary();
            JOptionPane.showMessageDialog(null, item.getName() + " has been removed!");
        }
    }
}
