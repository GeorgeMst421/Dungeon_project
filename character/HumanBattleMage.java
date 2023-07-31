package character;

import Enums.DamageType;
import Interfaces.Spellcasting;
import character.AbstractChars.AbstractBattleMage;

import java.util.Map;

public class HumanBattleMage extends AbstractBattleMage implements Spellcasting {
//    public Map<DamageType, Integer> defenses = Map.of(  DamageType.SLASHING,1,
//                                                        DamageType.BLUNT,1,
//                                                        DamageType.MAGICAL,0);
    public HumanBattleMage(String name){
        super();
        this.name = name;
        maxHP = currentHP = baseHP = 60;
        maxMP = currentMP = baseMP = 10;
        strength = baseStr = 9;
        intelligence = baseInt = 9;
        defenses = Map.of(  DamageType.SLASHING,1,
                            DamageType.BLUNT,1,
                            DamageType.MAGICAL,0);
        this.levelUP();
    }

    @Override
    public String getPlayerRace(){
        return "Human";
    }

}
