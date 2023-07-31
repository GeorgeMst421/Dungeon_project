package character.AbstractChars;

import Enums.DamageType;
import Interfaces.Spellcasting;

import java.util.Map;

public abstract class AbstractBattleMage extends AbstractPlayer implements Spellcasting {
    public AbstractBattleMage() {
        bonusHP = Map.of(   1,10,
                            2,13,
                            3,16,
                            4,20,
                            5,28,
                            6,40);

        bonusMP = Map.of(   1,10,
                            2,20,
                            3,30,
                            4,40,
                            5,50,
                            6,70);

        bonusStr = Map.of(  1,5,
                            2,7,
                            3,9,
                            4,11,
                            5,13,
                            6,15);

        bonusInt = Map.of(  1,5,
                            2,9,
                            3,13,
                            4,17,
                            5,21,
                            6,25);
    }

    public String getPlayerClass(){
        return "BattleMage";
    }

}
