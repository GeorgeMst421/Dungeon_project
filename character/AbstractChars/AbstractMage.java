package character.AbstractChars;

import Interfaces.Spellcasting;

import java.util.Map;

public abstract class AbstractMage extends AbstractPlayer implements Spellcasting {

    public AbstractMage() {

        bonusHP = Map.of(   1,8,
                            2,12,
                            3,16,
                            4,20,
                            5,24,
                            6,30);

        bonusMP = Map.of(   1,20,
                            2,40,
                            3,60,
                            4,80,
                            5,100,
                            6,120);

        bonusStr = Map.of(  1,2,
                            2,3,
                            3,4,
                            4,5,
                            5,6,
                            6,7);

        bonusInt = Map.of(  1,8,
                            2,16,
                            3,24,
                            4,32,
                            5,40,
                            6,48);
    }

    public String getPlayerClass(){
        return "Mage";
    }


}
