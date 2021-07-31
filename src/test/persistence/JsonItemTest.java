package persistence;

import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

// JsonItemTest superclass based on JsonSerializationDemo's JsonTest class
public class JsonItemTest {
    protected void checkItem(Item actualItem, Item item) {
        assertEquals(actualItem.getClass(), item.getClass());

        assertEquals(actualItem.getName(), item.getName());
        assertEquals(actualItem.getDescription(), item.getDescription());

        assertEquals(actualItem.getItemHealth(), item.getItemHealth());
        assertEquals(actualItem.getItemEnergy(), item.getItemEnergy());
        assertEquals(actualItem.getItemWeaponDamage(), item.getItemWeaponDamage());
        assertEquals(actualItem.getItemSpellDamage(), item.getItemSpellDamage());
        assertEquals(actualItem.getItemDefense(), item.getItemDefense());
        assertEquals(actualItem.getItemSpeed(), item.getItemSpeed());

        assertEquals(actualItem.getExtremifyCount(), item.getExtremifyCount());
    }
}
