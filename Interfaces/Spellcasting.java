package Interfaces;

import Enums.DamageType;
import character.AbstractChars.AbstractPlayer;

import java.util.Map;
public interface Spellcasting {

    // Separate method to calculate damage
    default Map<DamageType, Integer> calculateDamage(int intelligence) {
        return Map.of(DamageType.MAGICAL, intelligence);
    }

    // The castSpell method now uses calculateDamage
    default Map<DamageType, Integer> castSpell() {
        // 'this' keyword in an interface points to the implementing class
        if (this instanceof AbstractPlayer player) {
            if(player.getCurrentMP()>5) {
                player.reduceMP(5);
                return calculateDamage(player.getIntelligence());
            }
        }
        throw new IllegalStateException("Spellcasting interface can only be used by classes extending AbstractPlayer");
    }
}

