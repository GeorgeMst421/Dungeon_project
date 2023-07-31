package Interfaces;

import Enums.SlotType;

public interface Equippable extends Item {
    SlotType getSlotType();
    boolean isWeapon();
}
