package ui.tabs;

import model.WeaponList;
import ui.CombatantCreatorGUI;

/**
 * WeaponsTab subclass creates tab for selecting weapon
 */
public class WeaponsTab extends CategoryTab {

    private WeaponList weapons = new WeaponList();

    public WeaponsTab(CombatantCreatorGUI controller) {
        super(controller);

        placeCategoryName("Weapons");
        initializeItems(weapons.getItemList(),this);
        placeButtons();
    }
}
