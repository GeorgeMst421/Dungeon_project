package Items.Consumables;

import Enums.EffectType;
import Enums.ItemType;
import Items.ItemEffect;
import Interfaces.Consumable;
import Items.AbstractItem;

import java.util.ArrayList;
import java.util.List;

public class HealthPotion extends AbstractItem implements Consumable {
    private int uses;
    private final List<ItemEffect> bonusList = new ArrayList<>();

    public HealthPotion(){
        super("HP potion","Replenish some health");
        bonusList.add(new ItemEffect(EffectType.HP_REPLENISH,30));
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

