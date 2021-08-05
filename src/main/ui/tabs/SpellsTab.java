package ui.tabs;

import model.SpellList;
import ui.CombatantCreatorGUI;

public class SpellsTab extends CategoryTab {

    private SpellList spells = new SpellList();

    public SpellsTab(CombatantCreatorGUI controller) {
        super(controller);

        placeCategoryName("Spells");
        initializeItems(spells.getItemList(), this);
        placeButtons();
    }
}
