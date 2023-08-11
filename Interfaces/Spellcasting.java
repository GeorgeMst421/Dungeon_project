package Interfaces;

import Enums.DamageType;
import character.AbstractChars.AbstractPlayer;

import java.util.Map;
public interface Spellcasting {

    default Map<DamageType, Integer> calculateDamage(int intelligence) {
        return Map.of(DamageType.MAGICAL, intelligence);
    }
    default Map<DamageType, Integer> castSpell() {
        if (this instanceof AbstractPlayer player) {
                return calculateDamage(player.getIntelligence());
        }
        return null;
    }
}

