package Items.Weapons;

import Enums.ItemType;
import Items.ItemEffect;

import java.util.List;

public class EnemyWeapon extends AbstractWeapon {
    private final ItemType itemType = ItemType.ENEMY_WEAPON;

    public EnemyWeapon(String name, String description, List<Damage> damages) {
        super(name, description, damages);
    }

    @Override
    public ItemType getItemType() {
        return itemType;
    }
    @Override
    public List<ItemEffect> getItemEffects() {
        return null;
    }
}
