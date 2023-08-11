package character;

import Enums.DamageType;
import character.AbstractChars.AbstractWarrior;

import java.util.Map;

public class TaurenWarrior extends AbstractWarrior {

    public TaurenWarrior(String name){
        this.name = name;
        maxHP = currentHP = baseHP = 100;
        maxMP = currentMP = baseMP = 5;
        strength = baseStr = 12;
        intelligence = baseInt = 6;
        defenses = Map.of(  DamageType.SLASHING,2,
                            DamageType.BLUNT,1,
                            DamageType.MAGICAL,0);
        this.levelUP();
    }

    @Override
    public String getPlayerRace(){
        return "Tauren";
    }
}
