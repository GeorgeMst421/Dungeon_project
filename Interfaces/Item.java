package Interfaces;

import Enums.ItemType;
import Items.ItemEffect;

import java.util.List;

public interface Item {
    String getName();
    String getDescription();
    ItemType getItemType();
    List<ItemEffect> getItemEffects();

}
