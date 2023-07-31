package Items.Weapons;

import Enums.ItemType;
import Enums.SlotType;
import Items.ItemEffect;
import Interfaces.Equippable;

import java.util.List;

public class PlayerWeapon extends AbstractWeapon implements Equippable {
    private final List<ItemEffect> itemEffects;
    private final SlotType slotType;

    public PlayerWeapon(String name, String description, List<Damage> damages, List<ItemEffect> itemEffects, SlotType slotType) {
        super(name, description, damages);
        this.itemEffects = itemEffects;
        this.slotType = slotType;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.PLAYER_WEAPON;
    }

    @Override
    public List<ItemEffect> getItemEffects() {
        return itemEffects;
    }

    @Override
    public SlotType getSlotType() {
        return slotType;
    }

    @Override
    public boolean isWeapon() {
        return true;
    }
}
