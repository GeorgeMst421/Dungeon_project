package character;

import Enums.DamageType;
import character.AbstractChars.AbstractMage;

import java.util.Map;

public class OrcMage extends AbstractMage {

    public OrcMage(String name){
        this.name = name;
        maxHP = currentHP = baseHP = 80;
        maxMP = currentMP = baseMP = 10;
        strength = baseStr = 10;
        intelligence = baseInt = 8;
        defenses = Map.of(  DamageType.SLASHING,1,
                            DamageType.BLUNT,1,
                            DamageType.MAGICAL,0);
        this.levelUP();
    }

    @Override
    public String getPlayerRace(){
        return "Orc";
    }
}
