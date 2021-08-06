package ui.tabs;

import model.SpellList;
import ui.CombatantCreatorGUI;

/**
 * SpellsTab subclass creates tab for selecting spell
 */
public class SpellsTab extends CategoryTab {

    private SpellList spells = new SpellList();

    public SpellsTab(CombatantCreatorGUI controller) {
        super(controller);

        placeCategoryName("Spells");
        initializeItems(spells.getItemList(), this);
        placeButtons();
    }
}
