package Items.Weapons;

import Enums.DamageType;
import Game.Dice;

public class Damage {
    private final DamageType damageType;
    private final Dice dice;
    private final int diceBonus;

    public Damage(DamageType damageType, Dice dice, int diceBonus) {
        this.damageType = damageType;
        this.dice = dice;
        this.diceBonus = diceBonus;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public int roll() {
        return dice.Roll(diceBonus);
    }
}
