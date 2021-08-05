package ui.tabs;

import model.ArmourList;
import ui.CombatantCreatorGUI;

public class ArmoursTab extends CategoryTab {

    private ArmourList armours = new ArmourList();

    public ArmoursTab(CombatantCreatorGUI controller) {
        super(controller);

        placeCategoryName("Armours");
        initializeItems(armours.getItemList(), this);
        placeButtons();
    }
}
