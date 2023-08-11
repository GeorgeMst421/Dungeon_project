package Items.Consumables;

import Enums.EffectType;
import Enums.ItemType;
import Items.ItemEffect;
import Interfaces.Consumable;
import Items.AbstractItem;

import java.util.ArrayList;
import java.util.List;

public class ManaPotion extends AbstractItem implements Consumable {
    private int uses;
    private final List<ItemEffect> bonusList = new ArrayList<>();

    public ManaPotion(){
        super("MP potion","Replenish some mana");
        bonusList.add(new ItemEffect(EffectType.MP_REPLENISH,40));
        uses = 2;
    }
    @Override
    public ItemType getItemType() {
        return ItemType.CONSUMABLE;
    }

    @Override
    public List<ItemEffect> getItemEffects() {
        return bonusList;
    }

    @Override
    public int getUsesLeft() {
        return uses;
    }

    @Override
    public void use(){
        uses-=1;
    }
}
