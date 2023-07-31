package character.AbstractChars;

import Enums.SlotType;

import java.util.Map;

public abstract class AbstractWarrior extends AbstractPlayer {


    public AbstractWarrior(){
        bonusHP = Map.of(   1,10,
                            2,15,
                            3,20,
                            4,25,
                            5,30,
                            6,40);

        bonusStr = Map.of(  1,2,
                            2,4,
                            3,6,
                            4,8,
                            5,10,
                            6,12);

        bonusMP  = Map.of(1,0,2,0,3,0,4,0,5,0,6,0);
        bonusInt = Map.of(1,0,2,0,3,0,4,0,5,0,6,0);
    }

    public String getPlayerClass(){
        return "Warrior";
    }
}
