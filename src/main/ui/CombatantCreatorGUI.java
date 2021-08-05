package ui;

import model.ArmourList;
import model.Character;
import model.SpellList;
import model.WeaponList;
import org.json.JSONException;
import persistence.JsonLoader;
import ui.tabs.ArmoursTab;
import ui.tabs.SpellsTab;
import ui.tabs.SummaryTab;
import ui.tabs.WeaponsTab;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Combatant Creator Graphic User Interface
 */
public class CombatantCreatorGUI extends JFrame {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 800;
    public static final int WEAPONS_TAB_INDEX = 0;
    public static final int SPELLS_TAB_INDEX = 1;
    public static final int ARMOURS_TAB_INDEX = 2;
    public static final int SUMMARY_TAB_INDEX = 3;

    public static final String JSON_FILE = "./data/character.json";

    private Character character;

    private JsonLoader jsonLoader;

    private JTabbedPane tabBar;

    public CombatantCreatorGUI() {
        super("Combatant Creator");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        character = new Character();
        jsonLoader = new JsonLoader(JSON_FILE);

        startOptions();
        loadTabs();
        add(tabBar);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void loadTabs() {
        tabBar = new JTabbedPane();
        tabBar.setTabPlacement(JTabbedPane.LEFT);

        JPanel weaponTab = new WeaponsTab(this);
        JPanel spellTab = new SpellsTab(this);
        JPanel armourTab = new ArmoursTab(this);
        JPanel summaryTab = new SummaryTab(this);

        tabBar.add(weaponTab, WEAPONS_TAB_INDEX);
        tabBar.setTitleAt(WEAPONS_TAB_INDEX, "Weapons");
        tabBar.add(spellTab, SPELLS_TAB_INDEX);
        tabBar.setTitleAt(SPELLS_TAB_INDEX, "Spells");
        tabBar.add(armourTab, ARMOURS_TAB_INDEX);
        tabBar.setTitleAt(ARMOURS_TAB_INDEX, "Armours");
        tabBar.add(summaryTab, SUMMARY_TAB_INDEX);
        tabBar.setTitleAt(SUMMARY_TAB_INDEX, "Character Summary");
    }

    public void startOptions() {
        String[] options = {"Create New Character", "Load Saved Character"};
        int selection = JOptionPane.showOptionDialog(null, "How would you like to start?",
                "Welcome!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
        if (selection == 1) {
            try {
                character = jsonLoader.load();
                JOptionPane.showMessageDialog(null,
                        character.getCharName() + " is back for some changes!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Load error from file: " + JSON_FILE,
                        "Load Error", JOptionPane.WARNING_MESSAGE);
            } catch (JSONException e) {
                JOptionPane.showMessageDialog(null,
                        "No character saved! Creating new character...",
                        "Load Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public JTabbedPane getTabBar() {
        return tabBar;
    }

    public Character getCharacter() {
        return character;
    }
}
